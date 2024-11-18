package api.dto;

import api.entity.Section;
import api.entity.Settings;
import api.util.annotation.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto {

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
    private String lastName;

    @NotNull(message = "Cannot be null")
    private Section section;

    @NotNull(message = "Cannot be null")
    private Settings settings;

    @NotEmpty(message = "Cannot be empty")
    private String image;

    public UserDto(String email, String firstName, String lastName, String image) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

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

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String lastName) {
        this.lastName = lastName;
    }

    public @NotEmpty(message = "Cannot be empty") String getImage() {
        return image;
    }

    public void setImage(@NotEmpty(message = "Cannot be empty") String image) {
        this.image = image;
    }
}
