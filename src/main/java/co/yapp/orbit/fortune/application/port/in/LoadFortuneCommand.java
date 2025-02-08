package co.yapp.orbit.fortune.application.port.in;

import co.yapp.orbit.fortune.application.exception.InvalidFortuneCommandException;

public record LoadFortuneCommand(Long userId) {
    public LoadFortuneCommand {
        if (userId == null) {
            throw new InvalidFortuneCommandException("user id는 null 일 수 없습니다.");
        }
    }
}
