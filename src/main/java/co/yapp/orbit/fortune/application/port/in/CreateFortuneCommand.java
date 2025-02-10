package co.yapp.orbit.fortune.application.port.in;

import co.yapp.orbit.fortune.application.exception.InvalidFortuneCommandException;
import lombok.Getter;

@Getter
public class CreateFortuneCommand {

    private final Long userId;

    public CreateFortuneCommand(String usreId) {
        if (usreId == null || usreId.isEmpty()) {
            throw new InvalidFortuneCommandException("user id는 null 또는 빈 문자열일 수 없습니다.");
        }

        Long parsedUserId = null;
        try {
            parsedUserId = Long.parseLong(usreId);
        } catch (NumberFormatException e) {
            throw new InvalidFortuneCommandException("user id는 숫자여야 합니다.");
        }

        this.userId = parsedUserId;
    }
}
