package co.yapp.orbit.prereservation.application.port.in;

import co.yapp.orbit.prereservation.application.exception.InvalidPreReservationCommandException;
import lombok.Getter;

@Getter
public class PreReservationCommand {
    private final String name;
    private final String phoneNumber;

    public PreReservationCommand(String name, String phoneNumber) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidPreReservationCommandException("이름(name)는 null 또는 빈 값일 수 없습니다.");
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidPreReservationCommandException("전화번호(phoneNumber)는 null 또는 빈 값일 수 없습니다.");
        }
        this.name = name;
        this.phoneNumber = phoneNumber;

    }
}