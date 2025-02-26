package api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Column(name = "short_name")
    private String shortName;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Column(name = "color")
    private String color;

    @JsonManagedReference
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    List<User> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String name) {
        this.name = name;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String getShortName() {
        return shortName;
    }

    public void setShortName(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String shortName) {
        this.shortName = shortName;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String getColor() {
        return color;
    }

    public void setColor(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String color) {
        this.color = color;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department department = (Department) o;
        return id == department.id && Objects.equals(name, department.name) && Objects.equals(shortName, department.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortName);
    }
}
