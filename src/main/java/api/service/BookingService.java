package api.service;

import api.entity.Booking;

import java.util.List;

public interface BookingService {

    List<Booking> getAllBookings();
    Booking getBookingById(int id);
    Booking saveBooking(Booking booking);
    Booking updateBooking(Booking booking);
    void deleteBooking(int id);

}
