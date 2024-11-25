package api.service.implementations;

import api.entity.Room;
import api.service.RoomService;
import api.util.comparator.RoomCapacityComparator;
import api.util.comparator.RoomNameComparator;
import api.util.exception.RoomNotFoundException;
import api.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Room> getAllRooms(String filter) {
        List<Room> rooms = roomRepository.findAll();
        if(filter != null && !filter.isEmpty()) {
            switch (filter) {
                case "capacity_asc" -> rooms.sort(new RoomCapacityComparator());
                case "capacity_desc" -> rooms.sort(new RoomCapacityComparator().reversed());
                case "name_asc" -> rooms.sort(new RoomNameComparator());
                case "name_desc" -> rooms.sort(new RoomNameComparator().reversed());
            }
        }
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
    public Room saveRoom(Room room){
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
