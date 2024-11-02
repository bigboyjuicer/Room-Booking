package api.service;

import api.entity.Room;
import api.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    @Transactional
    public Room getRoomById(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Room saveOrUpdateRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public void deleteRoom(int id) {
        roomRepository.deleteById(id);
    }
}
