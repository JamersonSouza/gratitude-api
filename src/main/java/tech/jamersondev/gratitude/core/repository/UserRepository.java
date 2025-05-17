package tech.jamersondev.gratitude.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.jamersondev.gratitude.core.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String login);
}
