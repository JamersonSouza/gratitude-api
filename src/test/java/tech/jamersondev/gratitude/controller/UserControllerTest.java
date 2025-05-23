package tech.jamersondev.gratitude.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.jamersondev.gratitude.core.controller.UserController;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.service.UserServiceImpl;
import tech.jamersondev.gratitude.payload.form.CreateUserForm;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private UserController userController;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("Test create user controller")
    void testUserController_WhenCreateUser_thenReturnSaveUser() throws Exception {
        CreateUserForm createUserFormAsMock = new CreateUserForm(
                "james@mail.com",
                "James",
                "123456"
        );

        User userMock = Mockito.mock(User.class);
        when(userMock.getIdentifier()).thenReturn(UUID.randomUUID());
        when(userMock.getName()).thenReturn("james");

        when(userService.create(any(CreateUserForm.class)))
                .thenReturn(userMock);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createUserFormAsMock)))
                .andExpect(status().isCreated());

    }

}
