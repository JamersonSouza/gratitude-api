package tech.jamersondev.gratitude.core.service;

import org.springframework.stereotype.Service;
import tech.jamersondev.gratitude.core.interfaces.CardService;
import tech.jamersondev.gratitude.core.model.Card;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.CardRepository;
import tech.jamersondev.gratitude.core.repository.UserRepository;
import tech.jamersondev.gratitude.exceptions.UserNotFoundException;
import tech.jamersondev.gratitude.payload.form.CreateCardForm;

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
}
