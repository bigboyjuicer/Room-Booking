package api.controller;

import api.dto.delete.BookingDeleteDto;
import api.dto.post.BookingCreateDto;
import api.dto.get.Schedule;
import api.entity.Booking;
import api.entity.User;
import api.security.MyUserDetails;
import api.service.BookingService;
import api.util.ApiResponse;
import api.util.mapper.UserInfoMapper;
import api.util.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get schedule for booking for a certain room by id and date")
    @GetMapping("/room/{id}/{date}")
    @Secured("USER")
    public ResponseEntity<ApiResponse> getScheduleForRoom(@PathVariable int id, @PathVariable @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        List<Schedule> schedule = bookingService.getBookingsByRoomIdAndDate(id, date);
        log.info("Got schedule for booking for the room with id: {} and date: {}", id, date);
        return ResponseEntity.ok().body(new ApiResponse(false, "All bookings successfully found",
                new HashMap<>() {{
                    put("bookings", schedule);
                }}, null));
    }

    @Operation(summary = "Create booking for a room")
    @PostMapping
    @Secured("USER")
    public ResponseEntity<ApiResponse> addBooking(@RequestBody BookingCreateDto booking, Authentication authentication) {
        User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
        Booking newBooking = bookingService.saveBooking(booking, user);
        log.info("Booking added successfully: {}", newBooking);
        Schedule schedule = new Schedule(
                newBooking.getTime(),
                "booked",
                UserInfoMapper.MAPPER.fromUser(newBooking.getUser()),
                newBooking.getUser().getDepartment() == null ? null : newBooking.getUser().getDepartment().getName());

        return new ResponseEntity<>(new ApiResponse(
                true,
                "Booking successfully added",
                new HashMap<>() {{ put("booking", schedule); }},
                null), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete booking from room")
    @DeleteMapping
    @Secured("USER")
    public ResponseEntity<ApiResponse> deleteBooking(@RequestBody BookingDeleteDto deleteDto) {
        bookingService.deleteBooking(deleteDto);
        log.info("Booking deleted successfully: {}", deleteDto);
        return ResponseEntity.ok().body(new ApiResponse(true, "Booking successfully deleted", null, null));
    }

}
