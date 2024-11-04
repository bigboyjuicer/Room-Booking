package api.controller;

import api.entity.Room;
import api.util.exception.RoomNotFoundException;
import api.service.RoomService;
import api.util.Response;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    public Response getAllRooms(@RequestBody(required = false) ObjectNode objectNode) {
        List<Room> rooms = roomService.getAllRooms();
        if(rooms.isEmpty()) {
            return new Response(true, "There are no rooms", null, null);
        } else {
            Map<String, Object> data = new HashMap<>() {{
                put("rooms", rooms);
            }};
            return new Response(true, "Got all rooms successfully", data, null);
        }
    }

    @PostMapping()
    public Response addRoom(@Valid @RequestBody Room room) {
        Room newRoom = roomService.saveRoom(room);
        Map<String, Object> data = new HashMap<>() {{
            put("room", newRoom);
        }};
        return new Response(true, "Room added successfully", data, null);
    }

    @DeleteMapping("/{id}")
    public Response deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
        return new Response(true, "Room deleted successfully", null, null);
    }

    @GetMapping("/{id}")
    public Response getRoomById(@PathVariable(name = "id") int id) {
        Room room = roomService.getRoomById(id);
        Map<String, Object> data = new HashMap<>() {{
            put("room", room);
        }};
        return new Response(true, "Room found successfully", data, null);
    }

    @PutMapping("/{id}")
    public Response updateRoom(@Valid @RequestBody Room room, @PathVariable int id) {
        room.setId(id);
        Room updatedRoom = roomService.updateRoom(room);
        Map<String, Object> data = new HashMap<>() {{
            put("room", updatedRoom);
        }};
        return new Response(true, "Room updated successfully", data, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            FieldError fieldError = (FieldError) error;
            String fieldName = fieldError.getField();
            errors.put(fieldName, error.getDefaultMessage());
        });
        return new Response(false, "Validation error occurred", null, errors);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleRoomNotFoundException(RoomNotFoundException ex) {
        return new Response(false, ex.getMessage(), null, new HashMap<>() {{put("id", "Not found");}});
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleInvalidFormatException(Exception ex) {
        return new Response(false, ex.getMessage(), null, null);
    }

}
