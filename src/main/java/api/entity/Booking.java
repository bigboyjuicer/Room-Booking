package api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "room")
    @NotNull(message = "Cannot be null")
    private Room room;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "email")
    @NotNull(message = "Cannot be null")
    private User user;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @NotNull(message = "Cannot be null")
    @FutureOrPresent(message = "Date must be present or future")
    @Column(name = "time")
    private LocalDateTime time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Cannot be null") Room getRoom() {
        return room;
    }

    public void setRoom(@NotNull(message = "Cannot be null") Room room) {
        this.room = room;
    }

    public @NotNull(message = "Cannot be null") User getUser() {
        return user;
    }

    public void setUser(@NotNull(message = "Cannot be null") User user) {
        this.user = user;
    }

    public @NotNull(message = "Cannot be null") @FutureOrPresent() LocalDateTime getTime() {
        return time;
    }

    public void setTime(@NotNull(message = "Cannot be null") @FutureOrPresent() LocalDateTime time) {
        this.time = time;
    }
}
