package bg.softuni.hotel_reservation_system.model.mapper.user;

import bg.softuni.hotel_reservation_system.model.dto.user.EditUserRequest;
import bg.softuni.hotel_reservation_system.model.dto.user.UserDto;
import bg.softuni.hotel_reservation_system.model.dto.user.UserRegisterRequest;
import bg.softuni.hotel_reservation_system.model.entity.User;
import bg.softuni.hotel_reservation_system.model.enums.UserRole;
import lombok.NoArgsConstructor;


public class UserMapper {
    private UserMapper() {
    }

    public static User toUserEntity(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        return User.builder()
                .username(userRegisterRequest.getUsername())
                .password(userRegisterRequest.getPassword())
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .email(userRegisterRequest.getEmail())
                .phoneNumber(userRegisterRequest.getPhoneNumber())
                .role(UserRole.USER)
                .build();
    }

    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    public static EditUserRequest toEditUserRequest(User user) {
        if (user == null) {
            return null;
        }

        return EditUserRequest.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public static void updateUserFromRequest(EditUserRequest editUserRequest, User user) {
        if (editUserRequest == null || user == null) {
            return;
        }
        user.setFirstName(editUserRequest.getFirstName());
        user.setLastName(editUserRequest.getLastName());
        user.setEmail(editUserRequest.getEmail());
        user.setPhoneNumber(editUserRequest.getPhoneNumber());
    }
}
