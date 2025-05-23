package tech.jamersondev.gratitude.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationTest {

    @InjectMocks
    private SecurityConfiguration securityConfiguration;


    @Test
    void shouldReturnPasswordEncodeCorrectly() {
        PasswordEncoder encoder = securityConfiguration.bCryptPasswordEncoder();
        String myPassword = "myPassword";
        String encodedPassword = encoder.encode(myPassword);

        assertNotEquals(myPassword, encodedPassword);
        assertTrue(encoder.matches(myPassword, encodedPassword));
    }

    @Test
    void shouldReturnAuthenticationManager() throws Exception {
        AuthenticationConfiguration mockConfig = mock(AuthenticationConfiguration.class);
        AuthenticationManager mockManager = mock(AuthenticationManager.class);

        when(mockConfig.getAuthenticationManager()).thenReturn(mockManager);
        AuthenticationManager authenticationManager = this.securityConfiguration.authenticationManager(mockConfig);

        assertEquals(mockManager, authenticationManager);
        verify(mockConfig, times(1)).getAuthenticationManager();
    }
}
