package tech.jamersondev.gratitude.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException ex) {
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
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred on the server. Please try again later.";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle("Internal Server Error");
        return ResponseEntity.status(status).body(problem);
    }
}
