package co.yapp.orbit.fortune.adapter.in.exception;

import co.yapp.orbit.fortune.adapter.out.exception.FortuneParsingException;
import co.yapp.orbit.fortune.adapter.out.exception.WebClientFetchException;
import co.yapp.orbit.fortune.application.exception.FortuneCreateInvalidUserException;
import co.yapp.orbit.fortune.application.exception.FortuneNotFoundException;
import co.yapp.orbit.fortune.application.exception.InvalidFortuneCommandException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "co.yapp.orbit.fortune.adapter.in")
public class FortuneExceptionHandler {

    @ExceptionHandler(InvalidFortuneCommandException.class)
    public ResponseEntity<String> handleInvalidFortuneCommandException(InvalidFortuneCommandException e) {
        log.error("Invalid Fortune Command Exception occured: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(FortuneParsingException.class)
    public ResponseEntity<String> handleFortuneParsingException(FortuneParsingException e) {
        log.error("Fortune Parsing Exception occured: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(WebClientFetchException.class)
    public ResponseEntity<String> handleFortuneFetchException(WebClientFetchException e) {
        log.error("Fortune Fetch Exception occured: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    }

    @ExceptionHandler(FortuneNotFoundException.class)
    public ResponseEntity<String> handleFortuneNotFoundException(FortuneNotFoundException e) {
        log.error("Fortune Not Found Exception occured: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(FortuneCreateInvalidUserException.class)
    public ResponseEntity<String> handleFortuneCreateInvalidUserException(FortuneCreateInvalidUserException e) {
        log.error("Fortune Create Invalid User Exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Exception occured: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
