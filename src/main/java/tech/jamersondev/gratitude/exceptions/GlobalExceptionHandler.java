package tech.jamersondev.gratitude.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException ex) {
        LOGGER.error("Invalid fields. ", ex);
        String detail = "Invalid fields";
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        problemDetail.setTitle("Failed Validation");

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        problemDetail.setProperty("errors", errors);
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAllExceptions(Exception ex) {
        LOGGER.error("An unexpected error occurred on the server. Please try again later. ", ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred on the server. Please try again later.";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle("Internal Server Error");
        return ResponseEntity.status(status).body(problem);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ProblemDetail> handlerUserNotFound(UserNotFoundException ex) {
        LOGGER.error("Usuário não encontrado: ", ex);
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handlerEmailAlreadyExist(EmailAlreadyExistsException ex) {
        LOGGER.error("E-mail %s informado já pertence a um usuário cadastrado.", ex);
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle(String.format("E-mail %s informado já pertence a um usuário cadastrado.", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

}
