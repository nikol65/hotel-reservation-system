package bg.softuni.hotel_reservation_system.model.mapper.payment;

import bg.softuni.hotel_reservation_system.model.dto.payment.PaymentDto;
import bg.softuni.hotel_reservation_system.model.entity.Payment;

public class PaymentMapper {

    private PaymentMapper() {
    }

    public static PaymentDto toPaymentDto(Payment payment) {


        if (payment == null) {
            return null;
        }

        return PaymentDto.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .paymentMethod(payment.getPaymentMethod())
                .paymentType(payment.getPaymentType())
                .paymentStatus(payment.getPaymentStatus())
                .reservationId(payment.getReservation().getId())
                .build();

    }
}
