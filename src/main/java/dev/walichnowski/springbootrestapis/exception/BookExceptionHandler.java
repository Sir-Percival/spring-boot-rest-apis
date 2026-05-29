package dev.walichnowski.springbootrestapis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class BookExceptionHandler
{
    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(BookNotFoundException exception)
    {
        BookErrorResponse response = new BookErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                Instant.now()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(Exception exception)
    {
        BookErrorResponse response = new BookErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid request",
                Instant.now()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
