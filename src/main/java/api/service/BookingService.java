package api.service;

import api.dto.BookingCreateDto;
import api.dto.BookingDto;
import api.dto.Schedule;
import api.entity.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface BookingService {

    List<Booking> getAllBookings();
    List<BookingDto> getBookingsByRoomId(int roomId);
    List<Schedule> getBookingsByRoomIdAndDate(int roomId, LocalDate date);
    Booking getBookingById(int id);
    Booking saveBooking(BookingCreateDto bookingCreateDto);
    Booking updateBooking(Booking booking);
    void deleteBooking(int id);

}
