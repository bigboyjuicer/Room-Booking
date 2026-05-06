package api.dto.get;

public class RoomProfileDto {

    private int id;

    private String name;

    private AddressProfileDto address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressProfileDto getAddress() {
        return address;
    }

    public void setAddress(AddressProfileDto address) {
        this.address = address;
    }
}
