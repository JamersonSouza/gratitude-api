package tech.jamersondev.gratitude.payload.form;

import tech.jamersondev.gratitude.core.model.Card;

import java.io.Serializable;

public record CardForm(String identifier) implements Serializable {
    public CardForm (Card card){
        this(card.getIdentifier().toString());
    }
}
