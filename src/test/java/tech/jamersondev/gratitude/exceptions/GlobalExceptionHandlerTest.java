package tech.jamersondev.gratitude.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    @DisplayName("Test should return problem detail when exception occurs")
    void testReturnProblemDetailWhenExceptionOccurs() {
        Exception ex = new Exception("Erro inesperado");

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleAllExceptions(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ProblemDetail body = response.getBody();
        assertNotNull(body);
        assertEquals("Erro inesperado", body.getDetail());
        assertEquals("Internal Server Error", body.getTitle());
    }

    @Test
    @DisplayName("Test should return problem detail when User not found")
    void testReturnProblemDetailWhenUserNotFound() {
        UserNotFoundException ex = new UserNotFoundException("User not found");
        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handlerUserNotFound(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ProblemDetail body = response.getBody();
        assertNotNull(body);
        assertEquals("User not found", body.getTitle());
    }
}
