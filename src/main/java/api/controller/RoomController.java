package api.controller;

import api.entity.Room;
import api.util.exception.RoomNotFoundException;
import api.service.RoomService;
import api.util.MyCustomResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    public ResponseEntity<MyCustomResponse> getAllRooms(@RequestBody(required = false) ObjectNode objectNode) {
        String filter = objectNode == null ? null : objectNode.get("filter") == null ? null : objectNode.get("filter").asText();
        List<Room> rooms = roomService.getAllRooms(filter);
        if(rooms.isEmpty()) {
            return ResponseEntity.ok().body(new MyCustomResponse(true, "There are no rooms", null, null));
        } else {
            Map<String, Object> data = new HashMap<>() {{
                put("rooms", rooms);
            }};
            return ResponseEntity.ok().body(new MyCustomResponse(true, "Got all rooms successfully", data, null));
        }
    }

    @PostMapping()
    //@Secured("ADMIN")
    public ResponseEntity<MyCustomResponse> addRoom(@Valid @RequestBody Room room) {
        Map<String, Object> data = new HashMap<>() {{
            put("room", roomService.saveRoom(room));
        }};
        return new ResponseEntity<>(new MyCustomResponse(true, "Room successfully added", data, null), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<MyCustomResponse> addImage(@PathVariable int id, MultipartFile image) {
        try {
            Room room = roomService.getRoomById(id);
            room.setImageData(image.getBytes());
            room.setImageType(image.getContentType());
            room.setImageName(image.getOriginalFilename());
            Map<String, Object> data = new HashMap<>() {{
                put("room", roomService.updateRoom(room));
            }};
            return ResponseEntity.ok().body(new MyCustomResponse(true, "Image successfully added", data, null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    //@Secured("ADMIN")
    public ResponseEntity<MyCustomResponse> deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok().body(new MyCustomResponse(true, "Room successfully deleted", null, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyCustomResponse> getRoomById(@PathVariable(name = "id") int id) {
        Room room = roomService.getRoomById(id);
        Map<String, Object> data = new HashMap<>() {{
            put("room", room);
        }};
        return ResponseEntity.ok().body(new MyCustomResponse(true, "Room successfully found", data, null));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<MyCustomResponse> getRoomImage(@PathVariable(name = "id") int id) {
        Room room = roomService.getRoomById(id);
        byte[] imageFile = room.getImageData();
        Map<String, Object> data = new HashMap<>() {{
            put("image", imageFile);
        }};
        return ResponseEntity.ok().body(new MyCustomResponse(true, "Image successfully found", data, null));
    }

    @PutMapping("/{id}")
    //@Secured("ADMIN")
    public ResponseEntity<MyCustomResponse> updateRoom(@Valid @RequestBody Room room, @PathVariable int id) {
        room.setId(id);
        Room updatedRoom = roomService.updateRoom(room);
        Map<String, Object> data = new HashMap<>() {{
            put("room", updatedRoom);
        }};
        return ResponseEntity.ok().body(new MyCustomResponse(true, "Room successfully updated", data, null));
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<MyCustomResponse> handleRoomNotFoundException(RoomNotFoundException ex) {
        return ResponseEntity.badRequest().body(new MyCustomResponse(false, ex.getMessage(), null, new HashMap<>() {{put("id", "Not found");}}));
    }

}
