package api.dto.delete;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class BookingDeleteDto {

    @NotNull(message = "Cannot be null")
    @FutureOrPresent(message = "Date must be present or future")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;

    @Positive(message = "Cannot be negative")
    private int roomId;

    public @NotNull(message = "Cannot be null") @FutureOrPresent(message = "Date must be present or future") LocalDateTime getTime() {
        return time;
    }

    public void setTime(@NotNull(message = "Cannot be null") @FutureOrPresent(message = "Date must be present or future") LocalDateTime time) {
        this.time = time;
    }

    public @Positive(message = "Cannot be negative") int getRoomId() {
        return roomId;
    }

    public void setRoomId(@Positive(message = "Cannot be negative") int roomId) {
        this.roomId = roomId;
    }
}
