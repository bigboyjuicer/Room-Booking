package api.controller;

import api.dto.BookingCreateDto;
import api.dto.BookingDto;
import api.dto.Schedule;
import api.entity.Booking;
import api.service.BookingService;
import api.util.ApiResponse;
import api.util.mapper.BookingMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get all bookings")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            return ResponseEntity.ok().body(new ApiResponse(true, "There are no bookings", null, null));
        } else {
            return ResponseEntity.ok().body(new ApiResponse(false, "All bookings successfully found",
                    new HashMap<>() {{
                        put("bookings", bookings);
                    }}, null));
        }
    }

    /*@GetMapping("/room/{id}")
    public ResponseEntity<ApiResponse> getBookingsByRoomId(@PathVariable int id) {
        List<BookingDto> bookings = bookingService.getBookingsByRoomId(id);
        if (bookings.isEmpty()) {
            return ResponseEntity.ok().body(new ApiResponse(true, "There are no bookings", null, null));
        } else {
            return ResponseEntity.ok().body(new ApiResponse(false, "All bookings successfully found",
                    new HashMap<>() {{
                        put("bookings", bookings);
                    }}, null));
        }
    }*/

    @GetMapping("/room/{id}")
    public ResponseEntity<ApiResponse> getBookingsByRoomIdAndDate(@PathVariable int id, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        List<Schedule> schedules = bookingService.getBookingsByRoomIdAndDate(id, date);
        return ResponseEntity.ok().body(new ApiResponse(false, "All bookings successfully found",
                new HashMap<>() {{
                    put("bookings", schedules);
                }}, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBookingById(@PathVariable int id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Booking successfully found", new HashMap<>() {{
            put("booking", booking);
        }}, null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addBooking(@RequestBody BookingCreateDto booking) {
        return new ResponseEntity<>(new ApiResponse(true, "Booking successfully added", new HashMap<>() {{
            put("booking", BookingMapper.MAPPER.toBookingDto(bookingService.saveBooking(booking)));
        }}, null), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateBooking(@PathVariable int id, @RequestBody Booking booking) {
        booking.setId(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Booking successfully updated", new HashMap<>() {{
            put("booking", bookingService.updateBooking(booking));
        }}, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Booking successfully deleted", null, null));
    }

}
