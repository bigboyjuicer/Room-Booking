package api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 2, message = "Length cannot be less than 2")
    @Column(name = "region")
    private String region;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 2, message = "Length cannot be less than 2")
    @Column(name = "city")
    private String city;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 2, message = "Length cannot be less than 2")
    @Column(name = "street")
    private String street;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    @Column(name = "building")
    private String building;

    /*@OneToOne
    @JoinColumn(name = "address", unique = true)
    private Room room;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 2) String getRegion() {
        return region;
    }

    public void setRegion(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 2) String region) {
        this.region = region;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 2) String getCity() {
        return city;
    }

    public void setCity(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 2) String city) {
        this.city = city;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 2) String getStreet() {
        return street;
    }

    public void setStreet(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") @Size(min = 2) String street) {
        this.street = street;
    }

    public @NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String getBuilding() {
        return building;
    }

    public void setBuilding(@NotNull(message = "Cannot be null") @NotEmpty(message = "Cannot be empty") String building) {
        this.building = building;
    }
}
