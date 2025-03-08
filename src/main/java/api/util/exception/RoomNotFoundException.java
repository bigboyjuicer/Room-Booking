package api.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomNotFoundException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(RoomNotFoundException.class);

    public RoomNotFoundException(String message, int id) {
        super(message);
        log.error("Room with this id not found (id: {})", id);
    }
}
