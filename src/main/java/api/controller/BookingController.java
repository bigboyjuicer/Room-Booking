package api.controller;

import api.entity.Booking;
import api.service.BookingService;
import api.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            return ResponseEntity.ok().body(new ApiResponse(true, "There are no bookins", null, null));
        } else {
            return ResponseEntity.ok().body(new ApiResponse(false, "All bookings successfully found",
                    new HashMap<>() {{
                        put("bookings", bookings);
                    }}, null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBookingById(@PathVariable int id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Booking successfully found", new HashMap<>() {{
            put("booking", booking);
        }}, null));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> addBooking(@RequestBody Booking booking) {
        return new ResponseEntity<>(new ApiResponse(true, "Booking successfully added", new HashMap<>() {{
            put("booking", bookingService.saveBooking(booking));
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
