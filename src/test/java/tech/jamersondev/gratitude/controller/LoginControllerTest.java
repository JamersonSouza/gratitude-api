package tech.jamersondev.gratitude.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.jamersondev.gratitude.core.controller.LoginController;
import tech.jamersondev.gratitude.core.service.AuthenticationServiceImpl;
import tech.jamersondev.gratitude.payload.form.LoginForm;
import tech.jamersondev.gratitude.payload.form.TokenForm;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private LoginController loginController;

    @Mock
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }


    @Test
    @DisplayName("Test Login Controller")
    void givenValidCredentials_whenLogin_thenReturnsOkResponse() throws Exception {

        LoginForm loginForm = new LoginForm("jamerson@protonmail.com", "12345678");
        String token =UUID.randomUUID().toString();

        when(authenticationService.authenticationAndGenerateToken(loginForm)).thenReturn(token);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginForm)))
                .andExpect(status().isOk());

    }
}
