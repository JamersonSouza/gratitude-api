package tech.jamersondev.gratitude.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.UserRepository;
import tech.jamersondev.gratitude.core.service.UserDetailServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    @Test
    void shouldReturnUserDetailsWhenUserExists() {
        User userAsMock = Mockito.mock(User.class);
        when(userAsMock.getEmail()).thenReturn("james@mail.com");
        when(userAsMock.getPassword()).thenReturn("123456");

        when(userRepository.findByEmail(userAsMock.getEmail()))
                .thenReturn(Optional.of(userAsMock));

        UserDetails userDetails = userDetailService.loadUserByUsername(userAsMock.getEmail());

        assertNotNull(userDetails);
        assertEquals("123456", userDetails.getPassword());
    }
}
