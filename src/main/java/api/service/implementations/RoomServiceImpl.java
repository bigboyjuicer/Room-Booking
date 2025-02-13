package api.service.implementations;

import api.dto.Pagination;
import api.entity.Room;
import api.entity.Weekday;
import api.service.RoomService;
import api.util.exception.RoomNotFoundException;
import api.repository.RoomRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final String UPLOAD_DIR = "src/main/resources/static/images/";

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms(String filter) {
        List<Room> rooms = new ArrayList<>();
        if (filter != null && !filter.isEmpty()) {
            switch (filter) {
                case "capacity_asc" -> rooms = roomRepository.findAll(Sort.by(Sort.Direction.ASC, "capacity"));
                case "capacity_desc" -> rooms = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "capacity"));
                case "name_asc" -> rooms = roomRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
                case "name_desc" -> rooms = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
            }
        } else {
            rooms = roomRepository.findAll();
        }
        //rooms.forEach(room -> Collections.sort(room.getWeekdays()));
        return rooms;
    }

    @Override
    public Room getRoomById(int id) {
        if (roomRepository.findById(id).isPresent()) {
            Room room = roomRepository.findById(id).get();
            Collections.sort(room.getWeekdays());
            return roomRepository.findById(id).get();
        } else {
            throw new RoomNotFoundException("Room with this id not found");
        }
    }

    @Override
    public Room saveRoom(Room room, MultipartFile image) throws IOException {
        String imagePath = saveImage(image);
        room.setImagePath(imagePath);

        //Collections.sort(newRoom.getWeekdays());
        return roomRepository.save(room);
    }

    private String saveImage(MultipartFile image) throws IOException {
        File uploadPath = new File(UPLOAD_DIR);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        File targetFile = new File(uploadPath, fileName);
        image.transferTo(targetFile.getAbsoluteFile());

        return UPLOAD_DIR + fileName;
    }

    public List<String> getAvailableDaysInRoom(int id, Pagination pagination) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            return makeDayList(room, pagination);
        } else {
            throw new RoomNotFoundException("Room with this id not found");
        }
    }

    private int getDayOfWeek() {
        LocalDate today = LocalDate.now();
        return today.getDayOfWeek().getValue();
    }

    private List<String> makeDayList(Room room, Pagination pagination) {
        List<String> days = new ArrayList<>();
        LocalDate today = LocalDate.now();

        if(pagination.getPage() > 1) {
            today = today.plusDays(pagination.getSize());
        }

        for (int i = 0; i < pagination.getSize(); i++) {
            for (Weekday weekday : room.getWeekdays()) {
                if(i == 0 && weekday.getDay() < getDayOfWeek()) {
                    continue;
                }
                if (weekday.isActive()) {
                    days.add(today.toString());
                    i++;
                }
                today = today.plusDays(1);
            }
        }
        return days;
    }

    @Override
    public Path updateRoomImage(MultipartFile image, int id) throws IOException {
        if (roomRepository.findById(id).isPresent()) {
            Room room = roomRepository.findById(id).get();
            String newPath = saveImage(image);
            if(room.getImagePath() != null) {
                Files.deleteIfExists(Paths.get(room.getImagePath()));
            }
            room.setImagePath(newPath);
            roomRepository.save(room);
            return Paths.get(room.getImagePath()).normalize();
        } else {
            throw new RoomNotFoundException("Room with this id not found");
        }
    }

    @Override
    public Room updateRoom(Room room) {
        if (roomRepository.existsById(room.getId())) {
            //Room updatedRoom = roomRepository.save(room);
            //Collections.sort(updatedRoom.getWeekdays());
            return roomRepository.save(room);
        } else {
            throw new RoomNotFoundException("Room with this id not found");
        }
    }

    @Override
    public void deleteRoom(int id) throws IOException {
        if (roomRepository.findById(id).isPresent()) {
            if(roomRepository.findById(id).get().getImagePath() != null) {
                Files.deleteIfExists(Paths.get(roomRepository.findById(id).get().getImagePath()));
            }
            roomRepository.deleteById(id);
        } else {
            throw new RoomNotFoundException("Room with this id not found");
        }
    }
}
