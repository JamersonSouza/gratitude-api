package tech.jamersondev.gratitude.core.repository.speficiation;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import tech.jamersondev.gratitude.core.model.Card;
import tech.jamersondev.gratitude.core.model.User;

import java.util.UUID;

public class CardSpecification {

    private CardSpecification(){
    }

    public static Specification<Card> listByUser(String identifier){
        return (root, query, criteriaBuilder) -> {
            Join<Card, User> user = root.join("user");
            return criteriaBuilder.equal(user.get("identifier"), UUID.fromString(identifier));
        };
    }

    public static Specification<Card> distinct() {
        return ((root, query, criteriaBuilder) -> {
            if (query != null) {
                query.distinct(true);
            }
            return null;
        });
    }

}
