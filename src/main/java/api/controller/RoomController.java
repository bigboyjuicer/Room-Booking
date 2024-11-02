package api.controller;

import api.entity.Room;
import api.service.RoomService;
import api.util.Response;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Object> data = new HashMap<>() {{
            put("rooms", rooms);
        }};
        return new Response(true, "Got all rooms successfully", data);
    }

    @PostMapping()
    public Response addRoom(@RequestBody Room room) {
        Room newRoom = roomService.saveOrUpdateRoom(room);
        Map<String, Object> data = new HashMap<>() {{
            put("room", newRoom);
        }};
        return new Response(true, "Room added successfully", data);
    }

    @DeleteMapping()
    public Response deleteRoom(@RequestBody int id) {
        roomService.deleteRoom(id);
        return new Response(true, "Room deleted successfully", null);
    }

    @GetMapping("/{id}")
    public Response getRoomById(@PathVariable(name = "id") int id) {
        Room room = roomService.getRoomById(id);
        Map<String, Object> data = new HashMap<>() {{
            put("room", room);
        }};
        return new Response(true, "Room found successfully", data);
    }

    @PutMapping("/{id}")
    public Response updateRoom(@RequestBody Room room) {
        Room updatedRoom = roomService.saveOrUpdateRoom(room);
        Map<String, Object> data = new HashMap<>() {{
            put("room", updatedRoom);
        }};
        return new Response(true, "Room updated successfully", data);
    }

    @ExceptionHandler
    public Response handleException(Exception ex) {
        return new Response(false, ex.getMessage(), null);
    }
}
