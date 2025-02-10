package co.yapp.orbit.fortune.adapter.in.exception;

import co.yapp.orbit.fortune.adapter.out.exception.FortuneFetchException;
import co.yapp.orbit.fortune.application.exception.FortuneParsingException;
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

    @ExceptionHandler(FortuneFetchException.class)
    public ResponseEntity<String> handleFortuneFetchException(FortuneFetchException e) {
        log.error("Fortune Fetch Exception occured: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Exception occured: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
