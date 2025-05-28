package tech.jamersondev.gratitude.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.service.TokenService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() throws Exception {
        // Reflection para injetar o valor de "securityJwt"
        Field field = TokenService.class.getDeclaredField("securityJwt");
        field.setAccessible(true);
        field.set(tokenService, "my-secret-key-123");
    }

    @Test
    void shouldGenerateTokenWithValidSubject() {
        // Arrange
        User user = new User();
        user.setEmail("jamerson@mail.com");

        // Act
        String token = this.tokenService.generateToken(user);

        // Assert
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldExtractSubjectFromValidToken() {
        // Arrange
        User user = new User();
        user.setEmail("jamerson@mail.com");
        String token = tokenService.generateToken(user);

        // Act
        String subject = tokenService.getSubject(token);

        // Assert
        assertEquals(user.getEmail(), subject);
    }
}
