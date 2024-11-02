package api.service;

import api.entity.Room;

import java.util.List;

public interface RoomService {

    List<Room> getAllRooms();
    Room getRoomById(int id);
    Room saveOrUpdateRoom(Room room);
    void deleteRoom(int id);

}
