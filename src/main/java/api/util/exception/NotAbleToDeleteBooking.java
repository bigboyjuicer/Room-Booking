package api.util.exception;

public class NotAbleToDeleteBooking extends RuntimeException {
    public NotAbleToDeleteBooking(String message) {
        super(message);
    }
}
