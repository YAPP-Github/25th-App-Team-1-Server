package co.yapp.orbit.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import co.yapp.orbit.user.application.port.in.UpdateUserCommand;
import co.yapp.orbit.user.application.port.out.UpdateUserPort;
import co.yapp.orbit.user.domain.User;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class UpdateUserServiceTest {

    private UpdateUserPort updateUserPort;
    private UpdateUserService updateUserService;

    @BeforeEach
    void setUp() {
        updateUserPort = Mockito.mock(UpdateUserPort.class);
        updateUserService = new UpdateUserService(updateUserPort);
    }

    @Test
    @DisplayName("사용자 수정 시, 유효한 요청이면 내부에 올바른 도메인 객체를 전달한다.")
    void updateUser_success() {
        // given
        Long userId = 1L;
        UpdateUserCommand command = new UpdateUserCommand("홍길동", "2025-02-09", "08:30:00", "SOLAR", "MALE");

        // when
        updateUserService.updateUser(userId, command);

        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(updateUserPort, times(1)).update(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertEquals(userId, capturedUser.getId());
        assertEquals("홍길동", capturedUser.getName());
        assertEquals(LocalDate.parse("2025-02-09"), capturedUser.getBirthDate());
        assertEquals(LocalTime.parse("08:30:00"), capturedUser.getBirthTime());
        assertEquals("SOLAR", capturedUser.getCalendarType().toString());
        assertEquals("MALE", capturedUser.getGender().toString());
    }
}
