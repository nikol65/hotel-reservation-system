package bg.softuni.hotel_reservation_system.repository;

import bg.softuni.hotel_reservation_system.model.entity.Reservation;
import bg.softuni.hotel_reservation_system.model.entity.Room;
import bg.softuni.hotel_reservation_system.model.entity.User;
import bg.softuni.hotel_reservation_system.model.enums.ReservationStatus;
import bg.softuni.hotel_reservation_system.model.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByUser(User user);

    List<Reservation> findByRoom(Room room);

    List<Reservation> findByRoomAndStatusNot(Room room, ReservationStatus reservationStatus);

    List<Reservation> findByStatusAndCreatedAtBefore
            (ReservationStatus status, LocalDateTime createdAt);

    long countByUserId(UUID userId);
}
