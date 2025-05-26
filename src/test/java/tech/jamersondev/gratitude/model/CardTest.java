package tech.jamersondev.gratitude.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jamersondev.gratitude.core.enums.CardTypeEnum;
import tech.jamersondev.gratitude.core.model.Card;
import tech.jamersondev.gratitude.core.model.User;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CardTest {

    @Test
    @DisplayName("Test creating card persistence with date valid")
    void testCreateCardWithValidData() {

        UUID identifier = UUID.randomUUID();
        Long id = 1L;
        String color = "#fff";
        String text = "texto de teste";
        CardTypeEnum type = CardTypeEnum.DREAM;
        boolean isFavorite = false;
        Date createdDate = new Date();
        User user = mock(User.class);

        Card card = new Card();
        card.setIdentifier(identifier);
        card.setId(id);
        card.setColor(color);
        card.setText(text);
        card.setCardTypeEnum(type);
        card.setFavorite(isFavorite);
        card.setUser(user);
        card.setCreatedDate(createdDate);

        assertEquals(id, card.getId());
        assertEquals(identifier, card.getIdentifier());
        assertEquals(text, card.getText());
        assertEquals(type, card.getCardTypeEnum());
        assertEquals(color, card.getColor());
        assertEquals(user, card.getUser());
        assertEquals(createdDate, card.getCreatedDate());
        assertEquals(isFavorite, card.isFavorite());


    }
}
