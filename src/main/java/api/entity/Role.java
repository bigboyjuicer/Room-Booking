package api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "authorities")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "authority")
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    private String authority;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "email")
    @NotNull(message = "Cannot be null")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String getAuthority() {
        return authority;
    }

    public void setAuthority(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String authority) {
        this.authority = authority;
    }

    public @NotNull(message = "Cannot be null") User getUser() {
        return user;
    }

    public void setUser(@NotNull(message = "Cannot be null") User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && Objects.equals(authority, role.authority) && Objects.equals(user, role.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority, user);
    }
}
