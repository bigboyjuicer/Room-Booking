package api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    @Column(name = "image_path")
    private String imagePath;

    @Valid
    @NotNull(message = "Cannot be null")
    @Size(min = 7, max = 7, message = "Size of weekdays cannot be less or greater than 7")
    @JsonManagedReference
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Weekday> weekdays;

    public int countActiveWeekdays() {
        int count = 0;
        for (Weekday weekday : weekdays) {
            if (weekday.isActive()) {
                count++;
            }
        }
        return count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Weekday> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(List<Weekday> weekdays) {
        this.weekdays = weekdays;
    }
}
