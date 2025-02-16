package api.entity;

import api.util.annotation.IsActiveDependent;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.sql.Time;
import java.time.LocalTime;
import java.time.OffsetTime;

@Entity
@Table(name = "weekdays")
public class Weekday implements Comparable<Weekday> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Cannot be null")
    @Column(name = "day")
    @Positive(message = "Cannot be 0 or negative")
    @Max(value = 7, message = "Cannot be greater than 7")
    private int day;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "start_time")
    @IsActiveDependent(isActiveField = "isActive", message = "Start time must be not null when isActive is true")
    private LocalTime startTime;

    @Column(name = "end_time")
    @IsActiveDependent(isActiveField = "isActive", message = "End time must be not null when isActive is true")
    private LocalTime endTime;

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "room")
    @NotNull(message = "Cannot be null")
    private Room room;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public int compareTo(Weekday o) {
        return Integer.compare(this.getDay(), o.getDay());
    }
}
