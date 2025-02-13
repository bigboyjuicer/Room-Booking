package api.service;

import api.dto.Pagination;
import api.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface RoomService {

    List<Room> getAllRooms(String filter);
    Room getRoomById(int id);
    Room saveRoom(Room room, MultipartFile image) throws IOException;
    Room updateRoom(Room room);
    Path updateRoomImage(MultipartFile image, int id) throws IOException;
    void deleteRoom(int id) throws IOException;
    List<String> getAvailableDaysInRoom(int id, Pagination pagination);

}
