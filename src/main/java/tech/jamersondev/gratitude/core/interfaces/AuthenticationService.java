package tech.jamersondev.gratitude.core.interfaces;

import tech.jamersondev.gratitude.payload.form.LoginForm;

public interface AuthenticationService {

    String authenticationAndGenerateToken(LoginForm form);

}
