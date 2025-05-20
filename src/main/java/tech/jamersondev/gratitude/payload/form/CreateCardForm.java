package tech.jamersondev.gratitude.payload.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.jamersondev.gratitude.core.enums.CardTypeEnum;

public record CreateCardForm(@NotBlank(message = "field text is required") String text, String color, @NotNull(message = "field type is required") CardTypeEnum cardType, @NotBlank(message = "field color is required") String userId) {
}
