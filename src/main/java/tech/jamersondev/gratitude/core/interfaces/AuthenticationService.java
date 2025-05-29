package tech.jamersondev.gratitude.core.interfaces;

import tech.jamersondev.gratitude.payload.form.LoginForm;
import tech.jamersondev.gratitude.payload.form.RefreshTokenForm;
import tech.jamersondev.gratitude.payload.form.TokenForm;

public interface AuthenticationService {

    TokenForm authenticationAndGenerateToken(LoginForm form);

    String refreshToken(RefreshTokenForm form);
}
