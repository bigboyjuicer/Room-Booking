package api.entity;

import api.util.annotation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
    @Size(min = 2, message = "Number of characters must be greater than 1")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 6, message = "Number of characters must be greater than 5")
    @Column(name = "password")
    private String password;

    @Valid
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "department")
    private Department department;

    @Column(name = "image_path")
    private String image;

    @JoinColumn(name = "theme")
    @NotNull(message = "Cannot be null")
    @Enumerated(EnumType.STRING)
    @JdbcType(value = PostgreSQLEnumJdbcType.class)
    private Theme theme = Theme.System;

    /*@Valid
    @JsonBackReference
    @OneToMany(mappedBy = "")
    @JoinColumn(name = "settings")
    @NotNull(message = "Cannot be null")
    private List<Booking> bookings;*/

    @Valid
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

    public enum Theme {
        System,
        Dark,
        Light
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }



    public @NotNull(message = "Cannot be null") Theme getTheme() {
        return theme;
    }

    public void setTheme(@NotNull(message = "Cannot be null") Theme theme) {
        this.theme = theme;
    }

    public @Valid List<Role> getRoles() {
        return roles;
    }

    public void setRoles(@Valid List<Role> roles) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isAccountNonExpired() == user.isAccountNonExpired() && isAccountNonLocked() == user.isAccountNonLocked() && isCredentialsNonExpired() == user.isCredentialsNonExpired() && isEnabled() == user.isEnabled() && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getDepartment(), user.getDepartment()) && getTheme() == user.getTheme() && Objects.equals(getRoles(), user.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getFirstName(), getLastName(), getPassword(), getDepartment(), getTheme(), getRoles(), isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), isEnabled());
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", department=" + department +
                ", theme=" + theme +
                ", roles=" + roles +
                ", isAccountNonExpired=" + isAccountNonExpired +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", isCredentialsNonExpired=" + isCredentialsNonExpired +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
