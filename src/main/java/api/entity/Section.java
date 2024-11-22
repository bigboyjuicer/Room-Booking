package api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "sections")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Cannot be null")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Cannot be null")
    @Column(name = "short_name")
    private String shortName;

    /*@JsonManagedReference
    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<User> users;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Cannot be null") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Cannot be null") String name) {
        this.name = name;
    }

    public @NotNull(message = "Cannot be null") String getShortName() {
        return shortName;
    }

    public void setShortName(@NotNull(message = "Cannot be null") String shortName) {
        this.shortName = shortName;
    }

}
