package tech.jamersondev.gratitude.payload.form;

import tech.jamersondev.gratitude.core.model.Card;

public record CardForm(String identifier) {
    public CardForm (Card card){
        this(card.getIdentifier().toString());
    }
}
