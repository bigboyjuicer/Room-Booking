package api.dto.post;

import api.entity.Address;
import api.entity.Weekday;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RoomCreateDto {

    @Min(value = 1, message = "Cannot be less than 1")
    private int capacity;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    private String name;

    @Valid
    private Address address;

    private boolean isActive;

    @Valid
    @Size(min = 7, max = 7, message = "Size of weekdays cannot be less or greater than 7")
    @OrderBy("day ASC")
    private List<Weekday> weekdays;

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

    public @Valid @Size(min = 7, max = 7, message = "Size of weekdays cannot be less or greater than 7") List<Weekday> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(@Valid @Size(min = 7, max = 7, message = "Size of weekdays cannot be less or greater than 7") List<Weekday> weekdays) {
        this.weekdays = weekdays;
    }
}
