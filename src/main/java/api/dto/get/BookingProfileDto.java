package api.dto.get;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class BookingProfileDto {

    private int id;
    private RoomProfileDto room;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoomProfileDto getRoom() {
        return room;
    }

    public void setRoom(RoomProfileDto room) {
        this.room = room;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
