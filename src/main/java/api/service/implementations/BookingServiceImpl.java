package api.service.implementations;

import api.entity.Booking;
import api.repository.BookingRepository;
import api.service.BookingService;
import api.util.exception.BookingNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(int id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if(booking.isPresent()) {
            return booking.get();
        }
        else {
            throw new BookingNotFoundException("Booking with this id not found");
        }
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Booking booking) {
        if(bookingRepository.existsById(booking.getId())) {
            return bookingRepository.save(booking);
        } else {
            throw new BookingNotFoundException("Booking with this id not found");
        }
    }

    @Override
    public void deleteBooking(int id) {
        if(bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
        } else {
            throw new BookingNotFoundException("Booking with this id not found");
        }
    }
}
