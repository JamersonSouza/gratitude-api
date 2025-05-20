package tech.jamersondev.gratitude.payload.form;

import tech.jamersondev.gratitude.core.model.User;

public record UserForm(String identifier, String name) {
    public UserForm(User user) {
        this(user.getIdentifier().toString(), user.getName());
    }
}
