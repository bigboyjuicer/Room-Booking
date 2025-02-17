package api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;


@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Column(name = "theme")
    private String theme;

    /*@JsonManagedReference
    @OneToMany(mappedBy = "settings", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<User> users;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String getTheme() {
        return theme;
    }

    public void setTheme(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String theme) {
        this.theme = theme;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Settings settings = (Settings) o;
        return id == settings.id && Objects.equals(theme, settings.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme);
    }
}
