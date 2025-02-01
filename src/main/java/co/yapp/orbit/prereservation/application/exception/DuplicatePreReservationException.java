package co.yapp.orbit.prereservation.application.exception;

public class DuplicatePreReservationException extends RuntimeException {
    public DuplicatePreReservationException(String message) {
        super(message);
    }
}
