package tech.jamersondev.gratitude.payload.form;

import tech.jamersondev.gratitude.core.model.Card;

import java.io.Serializable;
import java.util.Date;

public record CardPageForm(String text, String color, boolean isFavorite, UserForm user, Date createdDate, String identifier, Date updatedDate) implements Serializable {
    public CardPageForm(Card card) {
        this(card.getText(), card.getColor(), card.isFavorite(),
                new UserForm(card.getUser()), card.getCreatedDate(), card.getIdentifier().toString(),
                card.getUpdatedDate());
    }
}
