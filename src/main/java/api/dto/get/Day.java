package api.dto.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

public class Day {

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;
    private boolean isActive;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long slots;

    public Day(LocalDate date, boolean isActive, long slots) {
        this.date = date;
        this.isActive = isActive;
        this.slots = slots;
    }

    public Day(LocalDate date, boolean isActive) {
        this.date = date;
        this.isActive = isActive;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getSlots() {
        return slots;
    }

    public void setSlots(long slots) {
        this.slots = slots;
    }
}
