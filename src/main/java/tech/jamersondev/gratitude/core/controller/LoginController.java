package tech.jamersondev.gratitude.core.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.service.TokenService;
import tech.jamersondev.gratitude.payload.form.LoginForm;
import tech.jamersondev.gratitude.payload.form.TokenForm;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenForm> login(@RequestBody @Valid LoginForm form){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.email(), form.password())
        );
        String token = this.tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new TokenForm(token));
    }
}
