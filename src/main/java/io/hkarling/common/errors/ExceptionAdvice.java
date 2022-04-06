package io.hkarling.common.errors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDeniedExceptionHandler(AccessDeniedException e) {
        e.printStackTrace();
        return ResponseEntity.status(FORBIDDEN).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .status(FORBIDDEN.value())
                .build());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> authenticationExceptionHandler(AuthenticationException e) {
        e.printStackTrace();
        return ResponseEntity.status(UNAUTHORIZED).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .status(UNAUTHORIZED.value())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> defaultExceptionHandler(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .status(INTERNAL_SERVER_ERROR.value())
                .build());
    }

}
