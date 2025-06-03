package tech.jamersondev.gratitude.payload.form;

import tech.jamersondev.gratitude.core.model.User;

import java.io.Serializable;

public record UserForm(String identifier, String name) implements Serializable {
    public UserForm(User user) {
        this(user.getIdentifier().toString(), user.getName());
    }
}
