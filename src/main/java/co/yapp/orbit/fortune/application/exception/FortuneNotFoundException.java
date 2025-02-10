package co.yapp.orbit.fortune.application.exception;

public class FortuneNotFoundException extends RuntimeException {
    public FortuneNotFoundException(String message) {
        super(message);
    }
}
