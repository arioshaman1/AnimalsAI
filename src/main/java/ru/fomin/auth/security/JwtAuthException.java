package ru.fomin.auth.security;

import io.jsonwebtoken.JwtException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
@Getter
public class JwtAuthException extends AuthenticationException {

    private HttpStatus status;

    public JwtAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtAuthException(String msg) {
        super(msg);
    }

    public JwtAuthException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }



}
