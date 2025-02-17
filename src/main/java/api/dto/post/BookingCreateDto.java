package api.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class BookingCreateDto {
    private int roomId;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
