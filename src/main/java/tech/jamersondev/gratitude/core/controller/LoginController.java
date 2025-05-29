package tech.jamersondev.gratitude.core.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jamersondev.gratitude.core.service.AuthenticationServiceImpl;
import tech.jamersondev.gratitude.payload.form.LoginForm;
import tech.jamersondev.gratitude.payload.form.RefreshTokenForm;
import tech.jamersondev.gratitude.payload.form.TokenForm;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationServiceImpl authenticationService;

    public LoginController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<TokenForm> login(@RequestBody @Valid LoginForm form){
        TokenForm tokens = this.authenticationService.authenticationAndGenerateToken(form);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenForm> refreshToken(@RequestBody RefreshTokenForm form){
        String newAccessToken = this.authenticationService.refreshToken(form);
        return ResponseEntity.ok(new TokenForm(newAccessToken, ""));
    }
}
