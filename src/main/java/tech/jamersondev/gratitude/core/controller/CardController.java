package tech.jamersondev.gratitude.core.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import tech.jamersondev.gratitude.core.model.Card;
import tech.jamersondev.gratitude.core.service.CardServiceImpl;
import tech.jamersondev.gratitude.payload.form.CardForm;
import tech.jamersondev.gratitude.payload.form.CreateCardForm;

import java.net.URI;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardServiceImpl cardServiceImpl;

    public CardController(CardServiceImpl cardServiceImpl) {
        this.cardServiceImpl = cardServiceImpl;
    }

    @PostMapping
    @Transactional(readOnly = false)
    public ResponseEntity<CardForm> createCard(@RequestBody @Valid CreateCardForm form, UriComponentsBuilder builder){
        Card card = this.cardServiceImpl.save(form);
        URI uri = builder.path("/card/{identifier}").buildAndExpand(card.getIdentifier()).toUri();
        return ResponseEntity.created(uri).body(new CardForm(card.getIdentifier().toString()));
    }
}
