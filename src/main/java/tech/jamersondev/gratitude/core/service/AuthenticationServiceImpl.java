package tech.jamersondev.gratitude.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.jamersondev.gratitude.core.interfaces.AuthenticationService;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.payload.form.LoginForm;
import tech.jamersondev.gratitude.payload.form.RefreshTokenForm;
import tech.jamersondev.gratitude.payload.form.TokenForm;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public TokenForm authenticationAndGenerateToken(LoginForm form) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.email(), form.password()));
        String token = this.tokenService.generateToken((User) auth.getPrincipal());
        String refreshToken = this.tokenService.generateRefreshToken((User) auth.getPrincipal());
        return new TokenForm(token, refreshToken);
    }

    @Override
    public String refreshToken(RefreshTokenForm form) {
        LOGGER.info("generating a new access token with refreshToken..");
        return this.tokenService.refreshAccessToken(form.refreshToken());
    }
}
