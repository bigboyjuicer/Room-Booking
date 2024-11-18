package api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Min(value = 1, message = "Cannot be less than 1")
    @Column(name = "capacity")
    private int capacity;

    @NotNull(message = "Cannot be null")
    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private boolean isActive = true;

    @JsonManagedReference
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Weekday> weekdays;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Min(value = 1, message = "Cannot be less than 1")
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(@Min(value = 1, message = "Cannot be less than 1") int capacity) {
        this.capacity = capacity;
    }

    public @NotNull(message = "Cannot be null") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Cannot be null") String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Weekday> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(List<Weekday> weekdays) {
        this.weekdays = weekdays;
    }
}
