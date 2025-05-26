package tech.jamersondev.gratitude.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.UserRepository;
import tech.jamersondev.gratitude.core.service.TokenService;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityFilterJwtTest {
    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;
    @InjectMocks
    private SecurityFilterJwt securityFilterJwt;

    @Test
    @DisplayName("test authentication user with security filter")
    void testAuthenticateUserWhenValidTokenIsPresent() throws ServletException, IOException {
        String token = "valid-jwt-token";
        String userEmail = "jamerson@mail.com";
        User mockUser = mock(User.class);

        when(request.getHeader("Authorization")).thenReturn(token);
        when(tokenService.getSubject(token)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(mockUser));
        when(mockUser.getAuthorities()).thenReturn(Collections.emptyList());

        securityFilterJwt.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(mockUser, authentication.getPrincipal());
        assertTrue(authentication.isAuthenticated());

        verify(filterChain).doFilter(request, response);
    }
}
