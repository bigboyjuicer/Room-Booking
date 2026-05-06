package api.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class BookingNotFoundException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(BookingNotFoundException.class);

    public BookingNotFoundException(String message, LocalDateTime time, int id) {
        super(message);
        log.error("Booking with this time: {} and roomId: {} not found", time, id);
    }
}
