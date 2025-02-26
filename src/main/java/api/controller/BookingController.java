package api.controller;

import api.dto.delete.BookingDeleteDto;
import api.dto.post.BookingCreateDto;
import api.dto.get.Schedule;
import api.entity.Booking;
import api.service.BookingService;
import api.util.ApiResponse;
import api.util.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get schedule for booking for a certain room by id and date")
    @GetMapping("/room/{id}/{date}")
    @Secured("USER")
    public ResponseEntity<ApiResponse> getScheduleForRoom(@PathVariable int id, @PathVariable @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        List<Schedule> schedule = bookingService.getBookingsByRoomIdAndDate(id, date);
        return ResponseEntity.ok().body(new ApiResponse(false, "All bookings successfully found",
                new HashMap<>() {{
                    put("bookings", schedule);
                }}, null));
    }

    @Operation(summary = "Create booking for a room")
    @PostMapping
    @Secured("USER")
    public ResponseEntity<ApiResponse> addBooking(@RequestBody BookingCreateDto booking) {
        Booking newBooking = bookingService.saveBooking(booking);
        Schedule schedule = new Schedule(newBooking.getTime(), "booked", UserMapper.MAPPER.fromUser(newBooking.getUser()), newBooking.getUser().getDepartment().getShortName());

        return new ResponseEntity<>(new ApiResponse(true, "Booking successfully added", new HashMap<>() {{
            put("booking", schedule);
        }}, null), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete booking from room")
    @DeleteMapping
    @Secured("USER")
    public ResponseEntity<ApiResponse> deleteBooking(@RequestBody BookingDeleteDto deleteDto) {
        bookingService.deleteBooking(deleteDto);
        return ResponseEntity.ok().body(new ApiResponse(true, "Booking successfully deleted", null, null));
    }

}
