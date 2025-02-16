package api.repository;

import api.entity.Booking;
import api.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<List<Booking>> findBookingsByRoomId(int roomId);

    @Query("SELECT b FROM Booking b WHERE b.room = :room and DATE(b.time) = :date")
    List<Booking> findBookingsByRoomIdAndDate(Room room, LocalDate date);
}
