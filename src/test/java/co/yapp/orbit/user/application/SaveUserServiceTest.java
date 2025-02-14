package co.yapp.orbit.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import co.yapp.orbit.user.application.port.in.SaveUserCommand;
import co.yapp.orbit.user.application.port.out.SaveUserPort;
import co.yapp.orbit.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SaveUserServiceTest {

    private SaveUserService saveUserService;
    private SaveUserPort saveUserPort;

    @BeforeEach
    void setUp() {
        saveUserPort = Mockito.mock(SaveUserPort.class);
        saveUserService = new SaveUserService(saveUserPort);
    }

    @Test
    @DisplayName("사용자 저장 시, 유효한 요청이면 생성된 사용자 id를 반환한다.")
    void saveUser_success() {
        // given
        SaveUserCommand command = new SaveUserCommand("홍길동", "2025-02-09", "08:30:00", "SOLAR", "MALE");
        Long expectedId = 1L;
        when(saveUserPort.save(any(User.class))).thenReturn(expectedId);

        // when
        Long userId = saveUserService.saveUser(command);

        // then
        assertEquals(expectedId, userId);
        verify(saveUserPort, times(1)).save(any(User.class));
    }
}
