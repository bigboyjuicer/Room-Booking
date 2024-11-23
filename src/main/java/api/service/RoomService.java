package api.service;

import api.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RoomService {

    List<Room> getAllRooms();
    Room getRoomById(int id);
    Room saveRoom(Room room, MultipartFile image) throws IOException;
    Room updateRoom(Room room);
    void deleteRoom(int id);

}
