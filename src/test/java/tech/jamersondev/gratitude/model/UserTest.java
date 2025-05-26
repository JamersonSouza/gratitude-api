package tech.jamersondev.gratitude.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jamersondev.gratitude.core.model.User;

import java.util.Date;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Test
    @DisplayName("Test creating object persistence with date valid")
    void testCreateUserWithValidData() {
        String email = "jamerson@mail.com";
        String name = "Jamerson";
        Date createdDate = new Date();
        String password = "12345789";
        UUID identifier = UUID.randomUUID();
        Long id = 1L;


        User user = new User();
        user.setIdentifier(identifier);
        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        user.setCreatedDate(createdDate);
        user.setPassword(password);

        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
        assertEquals(createdDate, user.getCreatedDate());
        assertEquals(password, user.getPassword());
        assertEquals(identifier, user.getIdentifier());
        assertEquals(id, user.getId());


    }
}
