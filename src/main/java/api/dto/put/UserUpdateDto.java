package api.dto.put;

import api.entity.Department;
import api.entity.User;
import api.util.annotation.ValidEmail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserUpdateDto {
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @ValidEmail(message = "Not valid email")
    private String email;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 2, message = "Number of characters must be greater than 1")
    private String firstName;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 2, message = "Number of characters must be greater than 1")
    private String lastName;

    @Valid
    private Department department;

    @NotNull(message = "Cannot be null")
    private User.Theme theme;

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String email) {
        this.email = email;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 2, message = "Number of characters must be greater than 1") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 2, message = "Number of characters must be greater than 1") String firstName) {
        this.firstName = firstName;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 2, message = "Number of characters must be greater than 1") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 2, message = "Number of characters must be greater than 1") String lastName) {
        this.lastName = lastName;
    }

    public @Valid Department getDepartment() {
        return department;
    }

    public void setDepartment(@Valid Department department) {
        this.department = department;
    }

    public @NotNull(message = "Cannot be null") User.Theme getTheme() {
        return theme;
    }

    public void setTheme(@NotNull(message = "Cannot be null") User.Theme theme) {
        this.theme = theme;
    }
}
