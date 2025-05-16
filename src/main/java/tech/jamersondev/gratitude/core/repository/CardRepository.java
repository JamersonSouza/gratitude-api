package tech.jamersondev.gratitude.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.jamersondev.gratitude.core.model.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}
