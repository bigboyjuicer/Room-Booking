package api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Cannot be null")
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

    public @NotNull(message = "Cannot be null") String getTheme() {
        return theme;
    }

    public void setTheme(@NotNull(message = "Cannot be null") String theme) {
        this.theme = theme;
    }
}
