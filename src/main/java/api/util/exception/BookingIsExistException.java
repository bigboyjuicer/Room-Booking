package api.util.exception;

public class BookingIsExistException extends RuntimeException {
    public BookingIsExistException(String message) {
        super(message);
    }
}
