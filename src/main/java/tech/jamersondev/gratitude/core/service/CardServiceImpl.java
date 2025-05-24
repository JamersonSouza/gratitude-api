package tech.jamersondev.gratitude.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.jamersondev.gratitude.core.interfaces.CardService;
import tech.jamersondev.gratitude.core.model.Card;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.CardRepository;
import tech.jamersondev.gratitude.core.repository.UserRepository;
import tech.jamersondev.gratitude.core.repository.speficiation.builder.CardSpecificationBuilder;
import tech.jamersondev.gratitude.exceptions.CardNotFoundException;
import tech.jamersondev.gratitude.exceptions.UserNotFoundException;
import tech.jamersondev.gratitude.payload.filters.CardFiltersForm;
import tech.jamersondev.gratitude.payload.form.CardPageForm;
import tech.jamersondev.gratitude.payload.form.CreateCardForm;
import tech.jamersondev.gratitude.payload.form.UpdateCardForm;

import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Card save(CreateCardForm form){
        User user = this.userRepository.findUserByIdentifier(UUID.fromString(form.userId())).orElseThrow(
                () -> new UserNotFoundException(String.format("User not found with identifier: %s", form.userId()))
        );
        Card card = new Card(form.cardType(), form.color(), form.text(), user);
        return this.cardRepository.save(card);
    }

    @Override
    public Page<CardPageForm> list(String userIdentifier, Pageable pageable) {
        Specification<Card> spec = CardSpecificationBuilder.builder()
                .withFilters(new CardFiltersForm(userIdentifier)).build();
        return this.cardRepository.findAll(spec, pageable).map(CardPageForm::new);
    }

    @Override
    public void delete(UUID cardIdentifier) {
        Card card = this.cardRepository.findCardByIdentifier(cardIdentifier)
                .orElseThrow(() -> new CardNotFoundException(String.format("Card not found with id %s", cardIdentifier)));
        this.cardRepository.delete(card);

    }

    @Override
    public Card update(UpdateCardForm form, UUID cardIdentifier) {
        Card card = this.cardRepository.findCardByIdentifier(cardIdentifier)
                .orElseThrow(() -> new CardNotFoundException(String.format("Card not found with id %s", cardIdentifier)));
        card.updateCard(form);
        return card;
    }
}
