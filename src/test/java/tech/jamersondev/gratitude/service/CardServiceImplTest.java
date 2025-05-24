package tech.jamersondev.gratitude.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import tech.jamersondev.gratitude.core.enums.CardTypeEnum;
import tech.jamersondev.gratitude.core.model.Card;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.CardRepository;
import tech.jamersondev.gratitude.core.repository.UserRepository;
import tech.jamersondev.gratitude.core.service.CardServiceImpl;
import tech.jamersondev.gratitude.payload.form.CardPageForm;
import tech.jamersondev.gratitude.payload.form.CreateCardForm;
import tech.jamersondev.gratitude.payload.form.UpdateCardForm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;


    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("test save new card with form valid")
    void testSave_withValidForm_returnsSavedCard() {
        //arange
        String userId = UUID.randomUUID().toString();
        CreateCardForm form = new CreateCardForm("texto de exemplo", "#fff", CardTypeEnum.DREAM,  userId);

        User userAsMock = Mockito.mock(User.class);
        when(userAsMock.getIdentifier()).thenReturn(UUID.fromString(userId));

        Card cardAsMock = Mockito.mock(Card.class);

        when(userRepository.findUserByIdentifier(userAsMock.getIdentifier())).thenReturn(Optional.of(userAsMock));
        when(cardRepository.save(any(Card.class))).thenReturn(cardAsMock);

        // Act
        Card card = cardService.save(form);

        // Assert
        assertEquals(cardAsMock, card);
        verify(userRepository).findUserByIdentifier(userAsMock.getIdentifier());
        verify(cardRepository).save(any(Card.class));
    }

    @Test
    @DisplayName("test delete card with identifier valid")
    void testeDelete_withIdentifierValid() {
        // arrange
        Card cardAsMock = Mockito.mock(Card.class);
        when(cardAsMock.getIdentifier()).thenReturn(UUID.randomUUID());

        when(cardRepository.findCardByIdentifier(cardAsMock.getIdentifier())).thenReturn(Optional.of(cardAsMock));

        // Act
        cardService.delete(cardAsMock.getIdentifier());

        // Assert
        verify(cardRepository).findCardByIdentifier(cardAsMock.getIdentifier());
        verify(cardRepository).delete(cardAsMock);
    }

    @Test
    @DisplayName("test update card with identifier valid")
    void testUpdate_withCardIdentifierValid_returnsCardUpdated() {
        //arange
        UpdateCardForm form = new UpdateCardForm("texto de exemplo", "#fff", CardTypeEnum.DREAM);

        Card cardAsMock = Mockito.mock(Card.class);
        when(cardAsMock.getIdentifier()).thenReturn(UUID.randomUUID());
        when(cardAsMock.getText()).thenReturn(form.text());
        when(cardAsMock.getColor()).thenReturn(form.color());
        when(cardAsMock.getCardTypeEnum()).thenReturn(form.cardType());


        when(cardRepository.findCardByIdentifier(cardAsMock.getIdentifier())).thenReturn(Optional.of(cardAsMock));

        // Act
        Card card = cardService.update(form, cardAsMock.getIdentifier());

        // Assert
        assertEquals(form.text(), card.getText());
        assertEquals(form.color(), card.getColor());
        assertEquals(form.cardType(), card.getCardTypeEnum());

        verify(cardRepository).findCardByIdentifier(cardAsMock.getIdentifier());
    }

    @Test
    @DisplayName("should return list of cards pagined ")
    void testList_withValidUserIdentifier_returnsCardPage() {
        // Arrange
        String userIdentifier = UUID.randomUUID().toString();
        Pageable pageable = PageRequest.of(0, 10);

        User user = Mockito.mock(User.class);
        when(user.getIdentifier()).thenReturn(UUID.randomUUID());

        Card card = new Card(CardTypeEnum.DREAM, "#FFF", "Example", user);

        Page<Card> cardPage = new PageImpl<>(List.of(card), pageable, 1);

        when(cardRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(cardPage);

        // Act
        Page<CardPageForm> result = cardService.list(userIdentifier, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(card.getText(), result.getContent().get(0).text());

        verify(cardRepository).findAll(any(Specification.class), eq(pageable));
    }


}
