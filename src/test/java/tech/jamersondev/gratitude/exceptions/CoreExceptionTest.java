package tech.jamersondev.gratitude.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoreExceptionTest {
    @Test
    void shouldCreateCoreExceptionWithMessage() {
        String message = "Exception Global message";
        CoreException exception = new CoreException(message);

        assertEquals(message, exception.getMessage());
    }
}
