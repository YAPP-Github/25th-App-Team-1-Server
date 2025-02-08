package co.yapp.orbit.fortune.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.yapp.orbit.fortune.application.exception.FortuneParsingException;
import co.yapp.orbit.fortune.application.port.in.CreateFortuneCommand;
import co.yapp.orbit.fortune.application.port.out.GeminiApiPort;
import co.yapp.orbit.fortune.application.port.out.SaveFortunePort;
import co.yapp.orbit.fortune.domain.Fortune;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateFortuneServiceTest {

    private CreateFortuneService createFortuneService;
    private GeminiApiPort geminiApiPort;
    private SaveFortunePort saveFortunePort;

    @BeforeEach
    void setUp() {
        geminiApiPort = Mockito.mock(GeminiApiPort.class);
        saveFortunePort = Mockito.mock(SaveFortunePort.class);
        createFortuneService = new CreateFortuneService(geminiApiPort, saveFortunePort, null);
    }

    @Test
    @DisplayName("운세 생성 요청이 들어오면 운세가 정상적으로 저장된다.")
    void createFortune_thenSave() {
        // given
        CreateFortuneCommand command = new CreateFortuneCommand("1");
        String mockResponse = "{ \"daily_fortune\": \"Good day!\", \"fortune\": { \"study_career\": {\"score\": 80, \"description\": \"Study well today!\"}, \"wealth\": {\"score\": 75, \"description\": \"A prosperous day!\"}, \"health\": {\"score\": 90, \"description\": \"Great health today!\"}, \"love\": {\"score\": 85, \"description\": \"Love is in the air!\"} }, \"lucky_outfit\": {\"top\": \"Blue\", \"bottom\": \"White\", \"shoes\": \"Black\", \"accessory\": \"Necklace\"}, \"unlucky_color\": \"Red\", \"lucky_color\": \"Green\", \"lucky_food\": \"Pizza\" }";

        when(geminiApiPort.loadFortune()).thenReturn(mockResponse);
        when(saveFortunePort.save(any(Fortune.class))).thenReturn(1L);

        // when
        Fortune fortune = createFortuneService.createFortune(command);

        // then
        verify(geminiApiPort, times(1)).loadFortune();
        verify(saveFortunePort, times(1)).save(any(Fortune.class));

        assertThat(fortune.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("운세 파싱 시 오류가 발생하면 FortuneParsingException이 발생한다.")
    void createFortune_whenParseError_thenThrowException() {
        // given
        CreateFortuneCommand command = new CreateFortuneCommand("1");
        String mockResponse = "{ invalid json }";

        when(geminiApiPort.loadFortune()).thenReturn(mockResponse);

        // when & then
        assertThrows(FortuneParsingException.class, () -> createFortuneService.createFortune(command));

        verify(saveFortunePort, never()).save(any(Fortune.class));
    }
}