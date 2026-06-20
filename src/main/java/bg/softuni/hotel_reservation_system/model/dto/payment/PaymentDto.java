package bg.softuni.hotel_reservation_system.model.dto.payment;

import bg.softuni.hotel_reservation_system.model.enums.PaymentMethod;
import bg.softuni.hotel_reservation_system.model.enums.PaymentStatus;
import bg.softuni.hotel_reservation_system.model.enums.PaymentType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PaymentDto {

    private UUID id;

    private BigDecimal amount;

    private LocalDateTime paymentDate;

    private PaymentType paymentType;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private UUID reservationId;
}
