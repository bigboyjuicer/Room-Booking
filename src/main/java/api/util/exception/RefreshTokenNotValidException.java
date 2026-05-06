package api.util.exception;

public class RefreshTokenNotValidException extends RuntimeException {
    public RefreshTokenNotValidException(String message) {
        super(message);
    }
}
