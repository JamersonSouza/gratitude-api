package tech.jamersondev.gratitude.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.service.AuthenticationServiceImpl;
import tech.jamersondev.gratitude.core.service.TokenService;
import tech.jamersondev.gratitude.payload.form.LoginForm;
import tech.jamersondev.gratitude.payload.form.TokenForm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    @DisplayName("test authentication should authenticate and generate token")
    void testAuthentication_validCredentials_returnsToken() {
        String email = "jamerson@mail.com";
        String password = "123456789";
        LoginForm form = mock(LoginForm.class);
        when(form.email()).thenReturn(email);
        when(form.password()).thenReturn(password);

        User user = new User();
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("mocked-jwt-token");

        // Act
        TokenForm tokenForm = authenticationService.authenticationAndGenerateToken(form);

        // Assert
        assertEquals("mocked-jwt-token", tokenForm.accessToken());

        verify(authenticationManager).authenticate(argThat(auth -> {
            return auth instanceof UsernamePasswordAuthenticationToken &&
                    (auth).getPrincipal().equals(email) &&
                    (auth).getCredentials().equals(password);
        }));
        verify(tokenService).generateToken(user);
    }
}

