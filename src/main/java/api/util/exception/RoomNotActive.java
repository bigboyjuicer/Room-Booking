package api.util.exception;

public class RoomNotActive extends RuntimeException {
    public RoomNotActive(String message) {
        super(message);
    }
}
