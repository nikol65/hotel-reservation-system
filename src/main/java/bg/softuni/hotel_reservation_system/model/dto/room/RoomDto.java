package bg.softuni.hotel_reservation_system.model.dto.room;

import bg.softuni.hotel_reservation_system.model.enums.RoomStatus;
import bg.softuni.hotel_reservation_system.model.enums.RoomType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class RoomDto {

    private UUID id;

    private Integer roomNumber;

    private RoomType roomType;

    private BigDecimal pricePerNight;

    private Integer capacity;

    private Integer floor;

    private String description;

    private RoomStatus roomStatus;
}
