package api.dto.post;

import api.util.annotation.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginUserDto {

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @ValidEmail(message = "Not valid email")
    private String email;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 6, message = "Number of characters must be greater than 5")
    private String password;

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String email) {
        this.email = email;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 6, message = "Number of characters must be greater than 5") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 6, message = "Number of characters must be greater than 5") String password) {
        this.password = password;
    }
}
