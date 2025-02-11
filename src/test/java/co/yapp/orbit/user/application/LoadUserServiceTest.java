package co.yapp.orbit.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import co.yapp.orbit.user.application.exception.UserNotFoundException;
import co.yapp.orbit.user.application.port.out.LoadUserPort;
import co.yapp.orbit.user.domain.CalendarType;
import co.yapp.orbit.user.domain.Gender;
import co.yapp.orbit.user.domain.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LoadUserServiceTest {

    private LoadUserService loadUserService;
    private LoadUserPort loadUserPort;

    @BeforeEach
    void setUp() {
        loadUserPort = Mockito.mock(LoadUserPort.class);
        loadUserService = new LoadUserService(loadUserPort);
    }

    @Test
    @DisplayName("사용자 조회 시, 존재하는 id이면 해당 사용자 객체를 반환한다.")
    void loadUser_success() {
        // given
        Long userId = 1L;
        User user = new User(
            userId,
            "홍길동",
            LocalDate.parse("2025-02-09"),
            LocalTime.parse("08:30:00"),
            CalendarType.SOLAR,
            Gender.MALE
        );
        when(loadUserPort.findById(userId)).thenReturn(Optional.of(user));

        // when
        User result = loadUserService.loadUser(userId);

        // then
        assertEquals(user, result);
    }

    @Test
    @DisplayName("사용자 조회 시, 존재하지 않는 id이면 UserNotFoundException을 발생시킨다.")
    void loadUser_notFound_throwsException() {
        // given
        Long userId = 1L;
        when(loadUserPort.findById(userId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(UserNotFoundException.class, () -> loadUserService.loadUser(userId));
    }
}
