package co.yapp.orbit.prereservation.adapter.in.exception;

import co.yapp.orbit.prereservation.application.exception.DuplicatePreReservationException;
import co.yapp.orbit.prereservation.application.exception.InvalidPreReservationCommandException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "co.yapp.orbit.prereservation.adapter.in")
public class PreReservationExceptionHandler {

    @ExceptionHandler(InvalidPreReservationCommandException.class)
    public ResponseEntity<String> handleInvalidPreReservationCommandException(InvalidPreReservationCommandException e) {
        log.error("Invalid PreReservation Command Exception occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    @ExceptionHandler(DuplicatePreReservationException.class)
    public ResponseEntity<String> handleDuplicatePreReservationException(DuplicatePreReservationException e) {
        log.error("Duplicate PreReservation Exception occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Unhandled Exception occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(e.getMessage());
    }
}
