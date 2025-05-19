package tech.jamersondev.gratitude.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.jamersondev.gratitude.core.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String login);

    @Query("SELECT u FROM User u WHERE u.identifier = :identifier")
    Optional<User> findByIdentifier(String identifier);
}
