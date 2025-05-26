package tech.jamersondev.gratitude.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardNotFoundExceptionTest {

    @Test
    void shouldCardNotFoundExceptionWithMessage() {
        String message = "Card not found Exception";
        CardNotFoundException exception = new CardNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

}
