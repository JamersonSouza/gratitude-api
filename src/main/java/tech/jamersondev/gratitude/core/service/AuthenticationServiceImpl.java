package tech.jamersondev.gratitude.core.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.jamersondev.gratitude.core.interfaces.AuthenticationService;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.payload.form.LoginForm;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public String authenticationAndGenerateToken(LoginForm form) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.email(), form.password()));
        return this.tokenService.generateToken((User) auth.getPrincipal());
    }
}
