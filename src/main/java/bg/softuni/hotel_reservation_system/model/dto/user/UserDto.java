package bg.softuni.hotel_reservation_system.model.dto.user;

import bg.softuni.hotel_reservation_system.model.enums.UserRole;
import lombok.*;
import org.springframework.context.support.BeanDefinitionDsl;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role;

}
