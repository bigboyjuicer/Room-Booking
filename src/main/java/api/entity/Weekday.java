package api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;

@Entity
@Table(name = "weekdays")
public class Weekday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Cannot be null")
    @Column(name = "day")
    private String day;

    @NotNull(message = "Cannot be null")
    @Column(name = "is_active")
    private boolean isActive = true;

    @NotNull(message = "Cannot be null")
    @Column(name = "start_time")
    private Time startTime;

    @NotNull(message = "Cannot be null")
    @Column(name = "end_time")
    private Time endTime;

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

    public @NotNull(message = "Cannot be null") String getDay() {
        return day;
    }

    public void setDay(@NotNull(message = "Cannot be null") String day) {
        this.day = day;
    }

    @NotNull(message = "Cannot be null")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(@NotNull(message = "Cannot be null") boolean active) {
        isActive = active;
    }

    public @NotNull(message = "Cannot be null") Time getStartTime() {
        return startTime;
    }

    public void setStartTime(@NotNull(message = "Cannot be null") Time startTime) {
        this.startTime = startTime;
    }

    public @NotNull(message = "Cannot be null") Time getEndTime() {
        return endTime;
    }

    public void setEndTime(@NotNull(message = "Cannot be null") Time endTime) {
        this.endTime = endTime;
    }
}
