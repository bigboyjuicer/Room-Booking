package api.service.implementations;

import api.dto.BookingCreateDto;
import api.dto.BookingDto;
import api.dto.Schedule;
import api.dto.UserDto;
import api.entity.Booking;
import api.entity.Room;
import api.entity.Section;
import api.entity.User;
import api.repository.BookingRepository;
import api.repository.RoomRepository;
import api.repository.UserRepository;
import api.service.BookingService;
import api.util.exception.BookingNotFoundException;
import api.util.exception.RoomNotFoundException;
import api.util.exception.UserNotFoundException;
import api.util.mapper.BookingMapper;
import api.util.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<BookingDto> getBookingsByRoomId(int roomId) {
        Optional<List<Booking>> bookings = bookingRepository.findBookingsByRoomId(roomId);
        if (bookings.isPresent()) {
            return BookingMapper.MAPPER.toBookingDtoList(bookings.get());
        } else {
            throw new BookingNotFoundException("Bookings with this room id not found");
        }
    }

    @Override
    public List<Schedule> getBookingsByRoomIdAndDate(int roomId, LocalDate date) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room with this id not found"));
        List<Schedule> schedules = generateListOfBookings(room, bookingRepository.findBookingsByRoomIdAndDate(room, date), date);
        return schedules;
    }

    private List<Schedule> generateListOfBookings(Room room, List<Booking> bookings, LocalDate date) {
        List<Schedule> schedules = new ArrayList<>();
        for (int i = getWeekdayStartHour(room, date); i <= getWeekdayEndHour(room, date); i++) {
            LocalDateTime time = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), i, 0, 0);
            String status = bookings.stream().anyMatch(b -> b.getTime().isEqual(time)) ? "booked" : "available";
            UserDto user = null;
            String section = null;
            if(status.equals("booked")) {
                //user = UserMapper.MAPPER.fromUser(bookings.stream().filter(b -> b.getTime().isEqual(time)).findFirst().get().getUser());
                section = bookings.stream().filter(b -> b.getTime().isEqual(time)).findFirst().get().getUser().getSection().getShortName();
            }
            schedules.add(new Schedule(time, status, user, section));
        }
        return schedules;
    }

    private int getWeekdayStartHour(Room room, LocalDate date) {
        int weekday = date.getDayOfWeek().getValue();
        return room.getWeekdays().get(weekday).getStartTime().getHour();
    }

    private int getWeekdayEndHour(Room room, LocalDate date) {
        int weekday = date.getDayOfWeek().getValue();
        return room.getWeekdays().get(weekday).getEndTime().getHour();
    }

    @Override
    public Booking getBookingById(int id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if(booking.isPresent()) {
            return booking.get();
        }
        else {
            throw new BookingNotFoundException("Booking with this id not found");
        }
    }

    @Override
    public Booking saveBooking(BookingCreateDto bookingCreateDto) {
        Room room = roomRepository.findById(bookingCreateDto.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Room with this id not found"));
        User user = userRepository.findById(bookingCreateDto.getUser()).orElseThrow(() -> new UserNotFoundException("User not found"));

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(user);
        booking.setTime(bookingCreateDto.getTime());

        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Booking booking) {
        if(bookingRepository.existsById(booking.getId())) {
            return bookingRepository.save(booking);
        } else {
            throw new BookingNotFoundException("Booking with this id not found");
        }
    }

    @Override
    public void deleteBooking(int id) {
        if(bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
        } else {
            throw new BookingNotFoundException("Booking with this id not found");
        }
    }
}
