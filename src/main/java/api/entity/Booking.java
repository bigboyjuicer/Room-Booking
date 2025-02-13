package api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;

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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "section")
    @NotNull(message = "Cannot be null")
    private Section section;

    @NotNull(message = "Cannot be null")
    @Column(name = "start_time")
    private Time startTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
}
