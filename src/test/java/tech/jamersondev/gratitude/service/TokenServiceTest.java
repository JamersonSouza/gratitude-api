package tech.jamersondev.gratitude.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.UserRepository;
import tech.jamersondev.gratitude.core.service.TokenService;
import tech.jamersondev.gratitude.exceptions.UserNotFoundException;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;



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

    @Test
    void shouldReturnNewAccessToken_whenRefreshTokenIsValid() {
        // Arrange
        String userEmail = "jamerson@mail.com";
        User user = new User();
        user.setEmail(userEmail);

        String refreshToken = JWT.create()
                .withSubject(userEmail)
                .withClaim("type", "refresh")
                .sign(Algorithm.HMAC256("my-secret-key-123"));

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));

        // Act
        String accessToken = tokenService.refreshAccessToken(refreshToken);

        // Assert
        assertNotNull(accessToken);
        verify(userRepository).findByEmail(userEmail);
    }

    @Test
    void shouldThrowException_whenUserNotFound() {
        // Arrange
        String userEmail = "jamerson@mail.com";
        String refreshToken = JWT.create()
                .withSubject(userEmail)
                .withClaim("type", "refresh")
                .sign(Algorithm.HMAC256("my-secret-key-123"));

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            tokenService.refreshAccessToken(refreshToken);
        });

        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    void shouldGenerateValidRefreshToken() {
        // Arrange
        User user = new User();
        user.setEmail("jamerson@protonmail.com");

        // Act
        String token = tokenService.generateRefreshToken(user);

        // Assert
        assertNotNull(token);

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("my-secret-key-123")).build().verify(token);
        assertEquals("jamerson@protonmail.com", decodedJWT.getSubject());
        assertEquals("refresh", decodedJWT.getClaim("type").asString());
        assertEquals("Gratitude App", decodedJWT.getIssuer());
    }
}
