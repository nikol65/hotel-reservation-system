package bg.softuni.hotel_reservation_system.service;

import bg.softuni.hotel_reservation_system.model.dto.reservation.ReservationCreateRequest;
import bg.softuni.hotel_reservation_system.model.dto.reservation.ReservationDto;
import bg.softuni.hotel_reservation_system.model.dto.room.RoomAddRequest;
import bg.softuni.hotel_reservation_system.model.entity.Reservation;
import bg.softuni.hotel_reservation_system.model.entity.Room;
import bg.softuni.hotel_reservation_system.model.entity.User;
import bg.softuni.hotel_reservation_system.model.enums.ReservationStatus;
import bg.softuni.hotel_reservation_system.model.enums.RoomStatus;
import bg.softuni.hotel_reservation_system.model.mapper.reservation.ReservationMapper;
import bg.softuni.hotel_reservation_system.repository.ReservationRepository;
import bg.softuni.hotel_reservation_system.repository.RoomRepository;
import bg.softuni.hotel_reservation_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    private boolean isRoomAvailable(Room room, LocalDateTime checkIn, LocalDateTime checkOut) {
        List<Reservation> reservations = reservationRepository.findByRoomAndStatusNot(room, ReservationStatus.CANCELLED);
        for (Reservation reservation : reservations) {
            if (checkIn.isBefore(reservation.getCheckOutDateTime()) &&
                    checkOut.isAfter(reservation.getCheckInDateTime())) {
                return false; // Room is not available
            }
        }
        return true; // Room is available
    }

    public ReservationDto createReservation(UUID userId, UUID roomId, ReservationCreateRequest request){
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));

        Room room = roomRepository.findById(roomId).orElseThrow(() ->
                new RuntimeException("Room not found"));

        if (request.getCheckInDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Check-in date cannot be before today");
        }

        if (room.getRoomStatus() == RoomStatus.INACTIVE) {
            throw new RuntimeException("Room is inactive");
        }

        if (request.getCheckInDate().isAfter(request.getCheckOutDate())
        || request.getCheckInDate().equals(request.getCheckOutDate())) {
            throw new RuntimeException("Check-out date must be after check-in date");
        }

        if(request.getNumberOfGuests() > room.getCapacity()) {
            throw new RuntimeException("Number of guests exceeds room capacity");
        }

        if(!isRoomAvailable(room, request.getCheckInDate().atTime(14, 0),
                                 request.getCheckOutDate().atTime(12, 0))) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        long numberOfNights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());

        BigDecimal totalPrice = BigDecimal.valueOf(numberOfNights).
                multiply(room.getPricePerNight());

        Reservation reservation = Reservation.builder()
                .user(user)
                .room(room)
                .checkInDateTime(request.getCheckInDate().atTime(14, 0))
                .checkOutDateTime(request.getCheckOutDate().atTime(12, 0))
                .status(ReservationStatus.PENDING)
                .totalPrice(totalPrice)
                .createdAt(LocalDateTime.now())
                .build();
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationMapper.toReservationDto(savedReservation);

    }
    public List<ReservationDto> findReservationsByUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return reservations.stream().map(ReservationMapper::toReservationDto).toList();
    }

    public ReservationDto findReservationByIdAndUser(UUID reservationId, UUID userId) {
        Reservation reservation =  reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (!reservation.getUser().getId().equals(userId)) {
            throw new RuntimeException("You cannot view another user's reservation");
        }
        return ReservationMapper.toReservationDto(reservation);

    }

    public ReservationDto cancelReservation(UUID reservationId, UUID userId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (!reservation.getUser().getId().equals(userId)) {
            throw new RuntimeException("You cannot cancel another user's reservation");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return ReservationMapper.toReservationDto(updatedReservation);
    }
}
