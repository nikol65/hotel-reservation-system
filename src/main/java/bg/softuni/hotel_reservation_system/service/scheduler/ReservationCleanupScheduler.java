package bg.softuni.hotel_reservation_system.service.scheduler;

import bg.softuni.hotel_reservation_system.model.entity.Reservation;
import bg.softuni.hotel_reservation_system.model.enums.ReservationStatus;
import bg.softuni.hotel_reservation_system.repository.PaymentRepository;
import bg.softuni.hotel_reservation_system.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationCleanupScheduler {
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    @Scheduled(fixedRate = 600000)
    public void autoCancelUnpaidReservations() {
        LocalDateTime expirationTime = LocalDateTime.now().minusHours(6);

        List<Reservation> expiredReservations =
                reservationRepository.findByStatusAndCreatedAtBefore(
                        ReservationStatus.PENDING,
                        expirationTime
                );

        for (Reservation reservation : expiredReservations) {
            if (!paymentRepository.existsByReservation(reservation)) {
                reservation.setStatus(ReservationStatus.AUTO_CANCELLED);
                reservationRepository.save(reservation);
            }
        }
    }
}
