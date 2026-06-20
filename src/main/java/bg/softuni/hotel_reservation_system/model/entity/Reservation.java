package bg.softuni.hotel_reservation_system.model.entity;
import bg.softuni.hotel_reservation_system.model.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDateTime checkInDateTime;

    @Column(nullable = false)
    private LocalDateTime checkOutDateTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
