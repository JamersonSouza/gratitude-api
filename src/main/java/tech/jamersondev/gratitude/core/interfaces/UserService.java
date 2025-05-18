package tech.jamersondev.gratitude.core.interfaces;

import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.payload.form.CreateUserForm;

public interface UserService {
    User create(CreateUserForm form);
}
