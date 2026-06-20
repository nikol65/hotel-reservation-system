package bg.softuni.hotel_reservation_system.repository;

import bg.softuni.hotel_reservation_system.model.entity.Room;
import bg.softuni.hotel_reservation_system.model.enums.RoomStatus;
import bg.softuni.hotel_reservation_system.model.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    boolean existsByRoomNumber(int roomNumber);
    Optional<Room> findByRoomNumber(int roomNumber);

    List <Room> findAllByRoomStatus(RoomStatus roomStatus);

    List<Room> findAllByRoomType(RoomType roomType);

}
