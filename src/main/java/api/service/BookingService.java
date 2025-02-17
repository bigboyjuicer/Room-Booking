package api.service;

import api.dto.delete.BookingDeleteDto;
import api.dto.get.BookingDto;
import api.dto.post.BookingCreateDto;
import api.dto.get.Schedule;
import api.entity.Booking;
import api.entity.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    List<Schedule> getBookingsByRoomIdAndDate(int roomId, LocalDate date);
    Booking saveBooking(BookingCreateDto bookingCreateDto);
    void deleteBooking(BookingDeleteDto deleteDto);

}
