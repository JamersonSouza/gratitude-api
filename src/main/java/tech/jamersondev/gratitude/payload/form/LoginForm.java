package tech.jamersondev.gratitude.payload.form;

import jakarta.validation.constraints.NotBlank;

public record LoginForm(@NotBlank String email, @NotBlank String password) {
}
