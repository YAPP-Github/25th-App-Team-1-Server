package co.yapp.orbit.prereservation.application.port.in;

import co.yapp.orbit.prereservation.application.exception.InvalidPreReservationCommandException;

public record PreReservationCommand(String email, String phoneNumber) {
    public PreReservationCommand {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidPreReservationCommandException("이메일(email)는 null 또는 빈 값일 수 없습니다.");
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidPreReservationCommandException("전화번호(phoneNumber)는 null 또는 빈 값일 수 없습니다.");
        }
    }
}