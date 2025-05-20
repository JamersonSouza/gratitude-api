package tech.jamersondev.gratitude.core.interfaces;

import jakarta.validation.Valid;
import tech.jamersondev.gratitude.core.model.Card;
import tech.jamersondev.gratitude.payload.form.CreateCardForm;

public interface CardService {
    Card save(@Valid CreateCardForm form);
}
