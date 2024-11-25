package api.service;

import api.entity.Room;
import java.util.List;

public interface RoomService {

    List<Room> getAllRooms(String filter);
    Room getRoomById(int id);
    Room saveRoom(Room room);
    Room updateRoom(Room room);
    void deleteRoom(int id);

}
