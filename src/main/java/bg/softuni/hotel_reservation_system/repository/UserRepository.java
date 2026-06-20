package bg.softuni.hotel_reservation_system.repository;

import bg.softuni.hotel_reservation_system.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

	boolean existsByUsername(String username);
	Optional<User> findByUsername(String username);

	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);
	Optional<User> findByPhoneNumber(String phoneNumber);
}
