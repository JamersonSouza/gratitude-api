package tech.jamersondev.gratitude.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.UserRepository;
import tech.jamersondev.gratitude.core.service.UserServiceImpl;
import tech.jamersondev.gratitude.payload.form.CreateUserForm;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testCreateNewUser(){
        CreateUserForm userMock = new CreateUserForm("Jamerson@protonmail.com", "Jamerson", passwordEncoder.encode("123456789"));

        User user = new User("Jamerson@protonmail.com", passwordEncoder.encode("123456789"), "Jamerson");
        user.setId(1L);
        user.setIdentifier(UUID.randomUUID());

        when(userRepository.save(any(User.class))).thenReturn(user);

        User userCreated = userService.create(userMock);

        assertNotNull(userCreated);
        assertEquals("Jamerson@protonmail.com", userCreated.getEmail());
        assertEquals("Jamerson", userCreated.getName());
        assertEquals(passwordEncoder.encode("123456789"), userCreated.getPassword());
    }
}
