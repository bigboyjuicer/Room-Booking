package api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Cannot be null")
    @Column(name = "region")
    private String region;

    @NotNull(message = "Cannot be null")
    @Column(name = "city")
    private String city;

    @NotNull(message = "Cannot be null")
    @Column(name = "street")
    private String street;

    @NotNull(message = "Cannot be null")
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    /*public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }*/
}
