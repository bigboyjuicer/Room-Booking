package api.service;

import api.entity.Room;
import api.util.exception.RoomNotFoundException;
import api.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    // TODO: Make Sorting
    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        rooms.forEach(room -> Collections.sort(room.getWeekdays()));
        return rooms;
    }

    @Override
    public Room getRoomById(int id) {
        if (roomRepository.findById(id).isPresent()) {
            Room room = roomRepository.findById(id).get();
            Collections.sort(room.getWeekdays());
            return room;
        } else {
            throw new RoomNotFoundException("Room with this id not found");
        }
    }

    @Override
    public Room saveRoom(Room room, MultipartFile image) throws IOException {
        room.setImageData(image.getBytes());
        room.setImageName(image.getOriginalFilename());
        room.setImageType(image.getContentType());
        Room newRoom = roomRepository.save(room);
        Collections.sort(newRoom.getWeekdays());
        return newRoom;
    }

    @Override
    public Room updateRoom(Room room) {
        if(roomRepository.existsById(room.getId())) {
            Room updatedRoom = roomRepository.save(room);
            Collections.sort(updatedRoom.getWeekdays());
            return roomRepository.save(room);
        } else {
            throw new RoomNotFoundException("Room with this id not found");
        }
    }

    @Override
    public void deleteRoom(int id) {
        if(roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
        } else {
            throw new RoomNotFoundException("Room with this id not found");
        }
    }
}
