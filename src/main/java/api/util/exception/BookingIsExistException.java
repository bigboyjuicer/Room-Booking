package api.util.exception;

import api.dto.post.BookingCreateDto;
import api.entity.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookingIsExistException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(BookingIsExistException.class);

    public BookingIsExistException(String message, Booking booking, BookingCreateDto bookingCreateDto) {
        super(message);
        log.error("Booking - {} is already exist. Booking in database - {}", bookingCreateDto, booking);
    }
}
