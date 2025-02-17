package api.dto.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Schedule {

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;
    private String status;
    private UserProfileDto user;
    private String section;

    public Schedule(LocalDateTime time, String status, UserProfileDto user, String section) {
        this.time = time;
        this.status = status;
        this.user = user;
        this.section = section;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserProfileDto getUser() {
        return user;
    }

    public void setUser(UserProfileDto user) {
        this.user = user;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
