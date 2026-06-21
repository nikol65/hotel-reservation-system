package bg.softuni.hotel_reservation_system.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20,
            message = "Username must be between 4 and 20 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+$",
            message = "Username can contain only letters, numbers and underscore"
    )
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit and one special character"
    )

    private String password;

    @NotBlank(message = "Confirm password is required")
    @Size(min = 8, max = 30, message = "Confirm password must be between 8 and 30 characters")
    private String confirmPassword;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    private String lastName;

    @NotBlank
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Pattern(
            regexp = "^[0-9+]+$",
            message = "Phone number can contain only digits and +"
    )
    private String phoneNumber;
}