package bg.softuni.hotel_reservation_system.model.dto.reservation;

import bg.softuni.hotel_reservation_system.model.enums.PaymentStatus;
import bg.softuni.hotel_reservation_system.model.enums.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ReservationDto {

    private UUID id;

    private Integer roomNumber;

    private LocalDateTime checkInDateTime;

    private LocalDateTime checkOutDateTime;

    private ReservationStatus status;

    private BigDecimal totalPrice;

    private PaymentStatus paymentStatus;
}
