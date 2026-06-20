package bg.softuni.hotel_reservation_system.model.dto.reservation;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationCreateRequest {

    @NotNull
    @FutureOrPresent(message = "Check-in date cannot be before today")
    private LocalDate checkInDate;

    @NotNull
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @NotNull
    @Min(1)
    private Integer numberOfGuests;
}
