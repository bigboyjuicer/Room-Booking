package api.service;

import api.entity.Room;
import api.util.exception.RoomNotFoundException;

import java.util.List;

public interface RoomService {

    List<Room> getAllRooms();
    Room getRoomById(int id) throws RoomNotFoundException;
    Room saveRoom(Room room);
    Room updateRoom(Room room);
    void deleteRoom(int id);

}
