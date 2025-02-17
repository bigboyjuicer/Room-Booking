package api.controller;

import api.dto.get.Day;
import api.dto.get.Pagination;
import api.dto.get.RoomDto;
import api.dto.post.RoomCreateDto;
import api.entity.Room;
import api.util.exception.RoomNotFoundException;
import api.service.RoomService;
import api.util.ApiResponse;
import api.util.mapper.RoomMapper;
import api.util.mapper.RoomCreateMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.core.io.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Get all rooms")
    @GetMapping()
    @Secured("USER")
    public ResponseEntity<ApiResponse> getAllRooms(@RequestBody(required = false) ObjectNode objectNode) {
        String filter = objectNode == null ? null : objectNode.get("filter") == null ? null : objectNode.get("filter").asText();
        List<RoomDto> rooms = RoomMapper.MAPPER.fromRooms(roomService.getAllRooms(filter));
        if(rooms.isEmpty()) {
            return ResponseEntity.ok().body(new ApiResponse(true, "There are no rooms", null, null));
        } else {
            Map<String, Object> data = new HashMap<>() {{
                put("rooms", rooms);
            }};
            return ResponseEntity.ok().body(new ApiResponse(true, "Got all rooms successfully", data, null));
        }
    }

    @Operation(summary = "Get room by ID")
    @GetMapping("/{id}")
    @Secured("USER")
    public ResponseEntity<ApiResponse> getRoomById(@PathVariable(name = "id") int id) {
        RoomDto room = RoomMapper.MAPPER.toRoomDto(roomService.getRoomById(id));
        Map<String, Object> data = new HashMap<>() {{
            put("room", room);
        }};
        return ResponseEntity.ok().body(new ApiResponse(true, "Room successfully found", data, null));
    }

    @Operation(summary = "Get image of the room by ID")
    @GetMapping("/{id}/image")
    @Secured("USER")
    public ResponseEntity<Resource> getRoomImage(@PathVariable int id) {
        Room room = roomService.getRoomById(id);

        Path filePath = Paths.get(room.getImagePath()).normalize();
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if(resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create new room")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> addRoom(@Valid @RequestPart("room") RoomCreateDto room, @RequestPart("image") MultipartFile image) {
        try {
            Map<String, Object> data = new HashMap<>() {{
                put("room", RoomMapper.MAPPER.toRoomDto(roomService.saveRoom(RoomCreateMapper.MAPPER.toRoom(room), image)));
            }};
            return new ResponseEntity<>(new ApiResponse(true, "Room successfully added", data, null), HttpStatus.CREATED);
        } catch (IOException ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage(), null, null), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update room image")
    @PutMapping("/{id}/image")
    @Secured("ADMIN")
    public ResponseEntity<Resource> updateImage(@PathVariable int id, MultipartFile image) {
        try {
            roomService.updateRoomImage(image, id);
            return getRoomImage(id);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Delete room by ID")
    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> deleteRoom(@PathVariable int id) {
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.ok().body(new ApiResponse(true, "Room successfully deleted", null, null));
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get days schedule for room")
    @GetMapping("/{id}/days")
    @Secured("USER")
    public ResponseEntity<ApiResponse> getRoomDaysScheduleById(@PathVariable(name = "id") int id, @Valid @RequestBody Pagination pagination) {
        List<Day> days = roomService.getAvailableDaysInRoom(id, pagination);
        Map<String, Object> data = new HashMap<>() {{
            put("days", days);
            put("pagination", pagination);
        }};
        return ResponseEntity.ok().body(new ApiResponse(true, "Days schedule successfully generated", data, null));
    }

    @Operation(summary = "Update room by ID")
    @PutMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<ApiResponse> updateRoom(@Valid @RequestBody RoomDto room, @PathVariable int id) {
        room.setId(id);
        Map<String, Object> data = new HashMap<>() {{
            put("room", RoomMapper.MAPPER.toRoomDto(roomService.updateRoom(RoomMapper.MAPPER.toRoom(room))));
        }};
        return ResponseEntity.ok().body(new ApiResponse(true, "Room successfully updated", data, null));
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ApiResponse> handleRoomNotFoundException(RoomNotFoundException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, ex.getMessage(), null, null));
    }

}
