package co.yapp.orbit.user.application.port.in;

import co.yapp.orbit.user.application.exception.InvalidUserCommandException;

public record UserCommand(String name, String birthDate, String birthTime, String gender) {
    public UserCommand {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidUserCommandException("이름(name)은 null 또는 빈 값일 수 없습니다.");
        }
        if (birthDate == null || birthDate.trim().isEmpty()) {
            throw new InvalidUserCommandException("생년월일(birthDate)는 null 또는 빈 값일 수 없습니다.");
        }
        if (birthTime == null || birthTime.trim().isEmpty()) {
            throw new InvalidUserCommandException("태어난시간(birthTime)은 null 또는 빈 값일 수 없습니다.");
        }
        if (gender == null || gender.trim().isEmpty()) {
            throw new InvalidUserCommandException("성별(gender)는 null 또는 빈 값일 수 없습니다.");
        }
    }
}
