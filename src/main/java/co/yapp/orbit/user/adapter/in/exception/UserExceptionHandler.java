package co.yapp.orbit.user.adapter.in.exception;

import co.yapp.orbit.user.application.exception.InvalidUserCommandException;
import co.yapp.orbit.user.application.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "co.yapp.orbit.user.adapter.in")
public class UserExceptionHandler {

    @ExceptionHandler(InvalidUserCommandException.class)
    public ResponseEntity<String> handleInvalidUserCommandException(InvalidUserCommandException e) {
        log.error("InvalidUserCommandException: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        log.error("UserNotFoundException: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Unhandled Exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(e.getMessage());
    }

}
