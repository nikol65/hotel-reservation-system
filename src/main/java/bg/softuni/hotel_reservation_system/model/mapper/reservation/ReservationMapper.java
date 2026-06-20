package bg.softuni.hotel_reservation_system.model.mapper.reservation;

import bg.softuni.hotel_reservation_system.model.dto.reservation.ReservationDto;
import bg.softuni.hotel_reservation_system.model.entity.Reservation;

public class ReservationMapper {

    private ReservationMapper() {
    }

    public static ReservationDto toReservationDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        return ReservationDto.builder()
                .id(reservation.getId())
                .roomNumber(reservation.getRoom().getRoomNumber())
                .checkInDateTime(reservation.getCheckInDateTime())
                .checkOutDateTime(reservation.getCheckOutDateTime())
                .status(reservation.getStatus())
                .totalPrice(reservation.getTotalPrice())
                .paymentStatus(
                        reservation.getPayment() != null
                                ? reservation.getPayment().getPaymentStatus()
                                : null
                )
                .build();
    }
}