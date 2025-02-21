package ru.fomin.auth.security.rest.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.fomin.auth.security.exception.UserNotFoundException;
import ru.fomin.auth.security.rest.model.UserErrorResponse;

@RestControllerAdvice
public class UserHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handlerUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .badRequest()
                .body(UserErrorResponse.builder()
                        .status(404)
                        .message(e.getMessage())
                        .build());
    }

}
