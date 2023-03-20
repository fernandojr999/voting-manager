package br.com.fernando.votingmanager.shared;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    HttpEntity<RestException> handleNotFoundException(NotFoundException ex) {
        return buildResponse(ex, NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    HttpEntity<RestException> handleRuntimeException(RuntimeException ex) {
        return buildResponse(ex, BAD_REQUEST);
    }

    private HttpEntity<RestException> buildResponse(Throwable ex, HttpStatus httpStatus) {
        return new ResponseEntity<>(new RestException(ex.getMessage()), httpStatus);
    }
}
