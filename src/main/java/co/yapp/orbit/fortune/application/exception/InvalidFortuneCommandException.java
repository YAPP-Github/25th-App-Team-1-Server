package co.yapp.orbit.fortune.application.exception;

public class InvalidFortuneCommandException extends RuntimeException {
  public InvalidFortuneCommandException(String message) {
    super(message);
  }
}
