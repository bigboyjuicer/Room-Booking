package api.service;

import api.entity.Room;
import api.util.exception.RoomNotFoundException;
import api.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(int id) {
        if (roomRepository.findById(id).isPresent()) {
            return roomRepository.findById(id).get();
        } else {
            throw new RoomNotFoundException("Room with this id not found");
        }
    }

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Room room) {
        if(roomRepository.existsById(room.getId())) {
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
