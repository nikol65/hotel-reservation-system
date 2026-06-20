package bg.softuni.hotel_reservation_system.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
