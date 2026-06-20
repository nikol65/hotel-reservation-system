package bg.softuni.hotel_reservation_system.service;

import bg.softuni.hotel_reservation_system.model.dto.payment.PaymentCreateRequest;
import bg.softuni.hotel_reservation_system.model.dto.payment.PaymentDto;
import bg.softuni.hotel_reservation_system.model.entity.Payment;
import bg.softuni.hotel_reservation_system.model.entity.Reservation;
import bg.softuni.hotel_reservation_system.model.enums.PaymentMethod;
import bg.softuni.hotel_reservation_system.model.enums.PaymentStatus;
import bg.softuni.hotel_reservation_system.model.enums.PaymentType;
import bg.softuni.hotel_reservation_system.model.enums.ReservationStatus;
import bg.softuni.hotel_reservation_system.model.mapper.payment.PaymentMapper;
import bg.softuni.hotel_reservation_system.repository.PaymentRepository;
import bg.softuni.hotel_reservation_system.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    public PaymentDto createPayment(UUID reservationId, UUID userId, PaymentCreateRequest request) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() ->
                new RuntimeException("Reservation not found"));

        if (paymentRepository.findByReservation(reservation).isPresent()) {
            throw new RuntimeException("Payment for this reservation already exists");

        }
        if(!reservation.getUser().getId().equals(userId)) {
            throw new RuntimeException("User is not authorized to make payment for this reservation");
        }

        if(reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new RuntimeException("Cannot make payment for a cancelled reservation");
        }
        if (request.getPaymentType() == PaymentType.ONLINE && request.getPaymentMethod() != PaymentMethod.BANK_TRANSFER) {
            throw new RuntimeException("Online payments must be made via bank transfer");
        }
        if (request.getPaymentType() == PaymentType.AT_HOTEL && request.getPaymentMethod() == PaymentMethod.BANK_TRANSFER) {
            throw new RuntimeException("At hotel payments cannot be made via bank transfer");
        }

        PaymentStatus paymentStatus = request.getPaymentType() ==
                PaymentType.ONLINE ? PaymentStatus.PAID : PaymentStatus.PENDING;
        Payment payment = Payment.builder()
                .reservation(reservation)
                .amount(reservation.getTotalPrice())
                .paymentType(request.getPaymentType())
                .paymentMethod(request.getPaymentMethod())
                .paymentDate(LocalDateTime.now())
                .paymentStatus(paymentStatus)
                .build();
        if(paymentStatus.equals(PaymentStatus.PAID)) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservationRepository.save(reservation);
        }
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentMapper.toPaymentDto(savedPayment);

    }

    public List<PaymentDto> findPaymentsByUser(UUID userId) {
        return paymentRepository.findByReservation_User_Id(userId)
                .stream()
                .map(PaymentMapper::toPaymentDto)
                .toList();
    }

    public BigDecimal calculateTotalSpentByUser(UUID userId) {
        return paymentRepository.findByReservation_User_Id(userId)
                .stream()
                .filter(payment -> payment.getPaymentStatus() == PaymentStatus.PAID)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
