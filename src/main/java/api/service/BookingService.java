package api.service;

import api.dto.delete.BookingDeleteDto;
import api.dto.post.BookingCreateDto;
import api.dto.get.Schedule;
import api.entity.Booking;
import api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    List<Schedule> getBookingsByRoomIdAndDate(int roomId, LocalDate date);
    Page<Booking> getUserBookings(User user, LocalDate date, Pageable pageable);
    Booking saveBooking(BookingCreateDto bookingCreateDto, User user);
    void deleteBooking(BookingDeleteDto deleteDto);

}
