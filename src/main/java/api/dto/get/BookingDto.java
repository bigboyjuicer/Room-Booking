package api.dto.get;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class BookingDto {

    private int id;
    private int roomId;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;

    public BookingDto(int id, int roomId, String firstName, String lastName, LocalDateTime time) {
        this.id = id;
        this.roomId = roomId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
