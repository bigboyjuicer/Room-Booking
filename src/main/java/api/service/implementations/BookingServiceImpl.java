package api.service.implementations;

import api.dto.delete.BookingDeleteDto;
import api.dto.get.Schedule;
import api.dto.get.UserInfoDto;
import api.dto.get.UserProfileDto;
import api.dto.post.BookingCreateDto;
import api.entity.Booking;
import api.entity.Room;
import api.entity.User;
import api.repository.BookingRepository;
import api.repository.RoomRepository;
import api.service.BookingService;
import api.util.exception.*;
import api.util.mapper.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Schedule> getBookingsByRoomIdAndDate(int roomId, LocalDate date) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room with this id not found", roomId));

        if(!room.getWeekdays().get(getDayOfWeek(date) - 1).isActive()){
            log.error("Room at this date: {} is not active. Room: {}, Date weekday: {}", date, room, date.getDayOfWeek().getValue());
            throw new WeekdayIsNotActiveException("Room at this date is not active");
        }

        return generateListOfBookings(room, bookingRepository.findBookingsByRoomIdAndDate(room, date).orElse(new ArrayList<>()), date);
    }

    private int getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }

    private List<Schedule> generateListOfBookings(Room room, List<Booking> bookings, LocalDate date) {
        List<Schedule> schedule = new ArrayList<>();

        for (int i = getWeekdayStartHour(room, date); i < getWeekdayEndHour(room, date); i++) {
            LocalDateTime time = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), i, 0, 0);
            String status = bookings.stream().anyMatch(b -> b.getTime().isEqual(time)) ? "booked" : "available";
            UserInfoDto user = null;
            String department = null;
            if(status.equals("booked")) {
                user = UserInfoMapper.MAPPER.fromUser(bookings.stream().filter(b -> b.getTime().isEqual(time)).findFirst().get().getUser());
                Booking booking = bookings.stream().filter(b -> b.getTime().isEqual(time)).findFirst().get();
                department = booking.getUser().getDepartment() == null ? "No department" : booking.getUser().getDepartment().getShortName();
            }
            schedule.add(new Schedule(time, status, user, department));
        }
        return schedule;
    }

    private int getWeekdayStartHour(Room room, LocalDate date) {
        return room.getWeekdays().get(getDayOfWeek(date) - 1).getStartTime().getHour();
    }

    private int getWeekdayEndHour(Room room, LocalDate date) {
        return room.getWeekdays().get(getDayOfWeek(date) - 1).getEndTime().getHour();
    }

    @Override
    public Page<Booking> getUserBookings(User user, LocalDate date, Pageable pageable) {
        return bookingRepository.findUserBookings(user, date, pageable);
    }

    @Override
    public Booking saveBooking(BookingCreateDto bookingCreateDto, User user) {
        Room room = roomRepository.findById(bookingCreateDto.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Room with this id not found", bookingCreateDto.getRoomId()));

        if(bookingRepository.findBookingByTimeAndRoom(bookingCreateDto.getTime(), room).isPresent()){
            throw new BookingIsExistException("Booking at this time already exists", bookingRepository.findBookingByTimeAndRoom(bookingCreateDto.getTime(), room).get(), bookingCreateDto);
        }

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(user);

        if(validateDateTime(bookingCreateDto.getTime(), room)) booking.setTime(bookingCreateDto.getTime());
        else throw new RoomNotActive("Room not active at this time");

        return bookingRepository.save(booking);
    }

    private boolean validateDateTime(LocalDateTime dateTime, Room room) {
        int startHour = room.getWeekdays().get(dateTime.getDayOfWeek().getValue() - 1).getStartTime().getHour();
        int endHour = room.getWeekdays().get(dateTime.getDayOfWeek().getValue() - 1).getEndTime().getHour() - 1;

        return startHour <= dateTime.getHour() && endHour >= dateTime.getHour();
    }

    @Override
    public void deleteBooking(BookingDeleteDto bookingDeleteDto) {
        Room room = roomRepository.findById(bookingDeleteDto.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Room with this id not found", bookingDeleteDto.getRoomId()));
        if(bookingRepository.findBookingByTimeAndRoom(bookingDeleteDto.getTime(), room).isPresent()) {
            Booking booking = bookingRepository.findBookingByTimeAndRoom(bookingDeleteDto.getTime(), room).get();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            if(user.getEmail().equals(booking.getUser().getEmail())) {
                bookingRepository.deleteByTimeAndRoom(bookingDeleteDto.getTime(), room);
            } else {
                throw new NotAbleToDeleteBooking("You are not allowed to delete this booking");
            }
        } else {
            throw new BookingNotFoundException("Booking with this time and roomId not found", bookingDeleteDto.getTime(), bookingDeleteDto.getRoomId());
        }
    }
}
