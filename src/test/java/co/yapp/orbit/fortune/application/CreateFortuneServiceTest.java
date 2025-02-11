package co.yapp.orbit.fortune.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.yapp.orbit.fortune.adapter.out.request.CreateFortuneRequest;
import co.yapp.orbit.fortune.application.exception.FortuneParsingException;
import co.yapp.orbit.fortune.application.port.in.CreateFortuneCommand;
import co.yapp.orbit.fortune.application.port.out.FortuneGenerationPort;
import co.yapp.orbit.fortune.application.port.out.SaveFortunePort;
import co.yapp.orbit.fortune.domain.Fortune;
import co.yapp.orbit.fortune.domain.FortuneItem;
import co.yapp.orbit.user.adapter.out.response.UserInfoResponse;
import co.yapp.orbit.user.application.port.out.UserApiPort;
import co.yapp.orbit.user.domain.CalendarType;
import co.yapp.orbit.user.domain.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateFortuneServiceTest {

    private CreateFortuneService createFortuneService;
    private FortuneGenerationPort fortuneGenerationPort;
    private UserApiPort userApiPort;
    private SaveFortunePort saveFortunePort;

    @BeforeEach
    void setUp() {
        fortuneGenerationPort = Mockito.mock(FortuneGenerationPort.class);
        saveFortunePort = Mockito.mock(SaveFortunePort.class);
        userApiPort = Mockito.mock(UserApiPort.class);
        createFortuneService = new CreateFortuneService(fortuneGenerationPort, saveFortunePort, userApiPort);
    }

    @Test
    @DisplayName("운세 생성 요청이 들어오면 운세가 정상적으로 저장된다.")
    void createFortune_thenSave() {
        // given
        CreateFortuneCommand command = new CreateFortuneCommand("1");
        Fortune fortune = Fortune.create(
            null,
            "Today is a great day!",
            new FortuneItem(90, "title1", "Success in studies"),
            new FortuneItem(90, "title2", "Prosperity in finance"),
            new FortuneItem(90, "title3", "Strong physical health"),
            new FortuneItem(90, "title4", "Harmonious love life"),
            "Red Shirt",
            "Black Pants",
            "White Sneakers",
            "Gold Necklace",
            "Green",
            "Blue",
            "Pizza"
        );
        UserInfoResponse userInfo = new UserInfoResponse(
            "name",
            "2025-02-09",
            "08:30:00",
            CalendarType.SOLAR.name(),
            Gender.MALE.name()
        );

        when(userApiPort.getUserInfo(1L)).thenReturn(userInfo);
        when(fortuneGenerationPort.loadFortune(any())).thenReturn(fortune);
        when(saveFortunePort.save(any(Fortune.class))).thenReturn(1L);

        // when
        Fortune fortuneResponse = createFortuneService.createFortune(command);

        // then
        verify(fortuneGenerationPort, times(1)).loadFortune(any());
        verify(saveFortunePort, times(1)).save(any(Fortune.class));

        assertThat(fortuneResponse.getId()).isEqualTo(1L);
    }
}