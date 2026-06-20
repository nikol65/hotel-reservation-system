package bg.softuni.hotel_reservation_system.model.dto.payment;

import bg.softuni.hotel_reservation_system.model.enums.PaymentMethod;
import bg.softuni.hotel_reservation_system.model.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentCreateRequest {

    @NotNull
    private PaymentType paymentType;

    @NotNull
    private PaymentMethod paymentMethod;
}
