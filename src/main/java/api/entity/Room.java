package api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Cannot be empty")
    @Column(name = "name")
    private String name;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    private Address address;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "image_path")
    //@NotNull(message = "Cannot be null")
    //@NotEmpty(message = "Cannot be empty")
    private String imagePath;

    @Valid
    @Size(min = 7, max = 7, message = "Size of weekdays cannot be less or greater than 7")
    @JsonManagedReference
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("day ASC")
    private List<Weekday> weekdays;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @Min(value = 1, message = "Cannot be less than 1") int getCapacity() {
        return capacity;
    }

    public void setCapacity(@Min(value = 1, message = "Cannot be less than 1") int capacity) {
        this.capacity = capacity;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String name) {
        this.name = name;
    }

    public @Valid Address getAddress() {
        return address;
    }

    public void setAddress(@Valid Address address) {
        this.address = address;
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

    public @Valid @Size(min = 7, max = 7, message = "Size of weekdays cannot be less or greater than 7") List<Weekday> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(@Valid @Size(min = 7, max = 7, message = "Size of weekdays cannot be less or greater than 7") List<Weekday> weekdays) {
        this.weekdays = weekdays;
    }
}
