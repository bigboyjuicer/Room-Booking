package api.dto.put;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChangePassword {
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 6, message = "Number of characters must be greater than 5")
    private String oldPassword;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 6, message = "Number of characters must be greater than 5")
    private String newPassword;

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 6, message = "Number of characters must be greater than 5") String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 6, message = "Number of characters must be greater than 5") String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 6, message = "Number of characters must be greater than 5") String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 6, message = "Number of characters must be greater than 5") String newPassword) {
        this.newPassword = newPassword;
    }
}
