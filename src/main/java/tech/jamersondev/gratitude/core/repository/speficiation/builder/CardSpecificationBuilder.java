package tech.jamersondev.gratitude.core.repository.speficiation.builder;

import tech.jamersondev.gratitude.core.model.Card;
import tech.jamersondev.gratitude.core.repository.speficiation.CardSpecification;
import tech.jamersondev.gratitude.payload.filters.CardFiltersForm;

public class CardSpecificationBuilder extends GlobalResourceBuilder<Card> {

    private CardSpecificationBuilder() {
        super(CardSpecification.distinct());
    }

    public static CardSpecificationBuilder builder() {
        return new CardSpecificationBuilder();
    }

    public CardSpecificationBuilder withFilters(CardFiltersForm filters) {
        if (filters == null) {
            return this;
        }

        if (filters.userIdentifier() != null && !filters.userIdentifier().isBlank()) {
            with(CardSpecification.listByUser(filters.userIdentifier()));
        }
        return this;
    }
}
