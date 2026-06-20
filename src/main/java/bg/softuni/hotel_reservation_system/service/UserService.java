package bg.softuni.hotel_reservation_system.service;

import bg.softuni.hotel_reservation_system.model.dto.room.RoomDto;
import bg.softuni.hotel_reservation_system.model.dto.user.EditUserRequest;
import bg.softuni.hotel_reservation_system.model.dto.user.UserDto;
import bg.softuni.hotel_reservation_system.model.dto.user.UserLoginRequest;
import bg.softuni.hotel_reservation_system.model.dto.user.UserRegisterRequest;
import bg.softuni.hotel_reservation_system.model.entity.Room;
import bg.softuni.hotel_reservation_system.model.entity.User;
import bg.softuni.hotel_reservation_system.model.mapper.room.RoomMapper;
import bg.softuni.hotel_reservation_system.model.mapper.user.UserMapper;
import bg.softuni.hotel_reservation_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean phoneNumberExists(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public UserDto findUserById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));
        return UserMapper.toUserDto(user);
    }

    public void register(UserRegisterRequest request) {
        if (usernameExists(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (emailExists(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (phoneNumberExists(request.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = UserMapper.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public UserDto login(UserLoginRequest userLoginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(userLoginRequest.getUsername());

        if (userOptional.isEmpty() ||
                !passwordEncoder.matches(userLoginRequest.getPassword(), userOptional.get().getPassword())
        ) {

            throw new RuntimeException("Username or password mismatch!");
        }

        return UserMapper.toUserDto(userOptional.get());
    }

    public UserDto updateProfile(UUID id, EditUserRequest editUserRequest) {
        User entity = userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("User with id [%s] does not exist.".formatted(id)));

        UserMapper.updateUserFromRequest(editUserRequest, entity);
        User updatedUser = userRepository.save(entity);

        return UserMapper.toUserDto(updatedUser);
    }
}
