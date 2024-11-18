package api.entity;

import api.util.annotation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @ValidEmail(message = "Not valid email")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 2, message = "Number of characters must be greater than 1")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Column(name = "password")
    private String password;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "section")
    @NotNull(message = "Cannot be null")
    private Section section;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "settings")
    @NotNull(message = "Cannot be null")
    private Settings settings;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Role> roles;

    @Column(name = "is_account_non_expired")
    private boolean isAccountNonExpired = true;

    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked = true;

    @Column(name = "is_credentials_non_expired")
    private boolean isCredentialsNonExpired = true;

    @Column(name = "is_enabled")
    private boolean isEnabled = true;

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

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String password) {
        this.password = password;
    }

    public @NotNull(message = "Cannot be null") Section getSection() {
        return section;
    }

    public void setSection(@NotNull(message = "Cannot be null") Section section) {
        this.section = section;
    }

    public @NotNull(message = "Cannot be null") Settings getSettings() {
        return settings;
    }

    public void setSettings(@NotNull(message = "Cannot be null") Settings settings) {
        this.settings = settings;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
