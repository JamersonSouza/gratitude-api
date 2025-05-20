package tech.jamersondev.gratitude.core.interfaces;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.jamersondev.gratitude.core.model.Card;
import tech.jamersondev.gratitude.payload.form.CardForm;
import tech.jamersondev.gratitude.payload.form.CardPageForm;
import tech.jamersondev.gratitude.payload.form.CreateCardForm;

public interface CardService {
    Card save(@Valid CreateCardForm form);

    Page<CardPageForm> list(Pageable pageable);
}
