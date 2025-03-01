package api.repository;

import api.entity.Booking;
import api.entity.Room;
import api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT b FROM Booking b WHERE b.room = :room and DATE(b.time) = :date")
    Optional<List<Booking>> findBookingsByRoomIdAndDate(Room room, LocalDate date);
    void deleteByTimeAndRoom(LocalDateTime time, Room room);
    Optional<Booking> findBookingByTimeAndRoom(LocalDateTime time, Room room);
    @Query("SELECT b FROM Booking b WHERE b.user = :user and DATE(b.time) >= :date")
    Page<Booking> findUserBookings(@Param("user") User user, @Param("date") LocalDate date, Pageable pageable);
}
