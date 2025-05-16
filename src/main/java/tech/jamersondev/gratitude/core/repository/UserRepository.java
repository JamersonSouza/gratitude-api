package tech.jamersondev.gratitude.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.jamersondev.gratitude.core.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
