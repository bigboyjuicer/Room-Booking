package api.util.exception;

public class WeekdayIsNotActiveException extends RuntimeException {
    public WeekdayIsNotActiveException(String message) {
        super(message);
    }
}
