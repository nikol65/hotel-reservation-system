package bg.softuni.hotel_reservation_system.model.entity;


import bg.softuni.hotel_reservation_system.model.enums.RoomDescription;
import bg.softuni.hotel_reservation_system.model.enums.RoomStatus;
import bg.softuni.hotel_reservation_system.model.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private int roomNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(nullable = false)
    private BigDecimal pricePerNight;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer floor;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

}
