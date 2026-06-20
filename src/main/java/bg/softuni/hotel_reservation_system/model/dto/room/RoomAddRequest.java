package bg.softuni.hotel_reservation_system.model.dto.room;

import bg.softuni.hotel_reservation_system.model.enums.RoomStatus;
import bg.softuni.hotel_reservation_system.model.enums.RoomType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoomAddRequest {

    @NotNull
    @Min(100)
    @Max(9999)
    private Integer roomNumber;

    @NotNull
    private RoomType roomType;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal pricePerNight;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer capacity;

    @NotNull
    @Min(1)
    @Max(50)
    private Integer floor;

    @Size(max = 255)
    private String description;

    @NotNull
    private RoomStatus roomStatus;
}
