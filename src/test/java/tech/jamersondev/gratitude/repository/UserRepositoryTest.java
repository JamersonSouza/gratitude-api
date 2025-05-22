package tech.jamersondev.gratitude.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Test find user by email")
    void testFindByEmail_WhenExists_ReturnsUser() {
        User userAsMock = Mockito.mock(User.class);
        when(userAsMock.getEmail()).thenReturn("james@mail.com");

        when(userRepository.findByEmail(userAsMock.getEmail()))
                .thenReturn(Optional.of(userAsMock));

        Optional<User> result = userRepository.findByEmail(userAsMock.getEmail());

        assertTrue(result.isPresent());
        assertEquals("james@mail.com", result.get().getEmail());

        verify(userRepository, times(1)).findByEmail(userAsMock.getEmail());
    }

    @Test
    @DisplayName("Test find user by uuid")
    void testFindByUuid_WhenExists_ReturnsUser() {
        User userAsMock = Mockito.mock(User.class);
        when(userAsMock.getIdentifier()).thenReturn(UUID.randomUUID());

        when(userRepository.findUserByIdentifier(userAsMock.getIdentifier()))
                .thenReturn(Optional.of(userAsMock));

        Optional<User> result = userRepository.findUserByIdentifier(userAsMock.getIdentifier());

        assertTrue(result.isPresent());
        assertEquals(userAsMock.getIdentifier(), result.get().getIdentifier());

        verify(userRepository, times(1)).findUserByIdentifier(userAsMock.getIdentifier());

    }
}
