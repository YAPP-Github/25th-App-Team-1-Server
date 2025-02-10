package co.yapp.orbit.fortune.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import co.yapp.orbit.fortune.application.exception.FortuneNotFoundException;
import co.yapp.orbit.fortune.application.port.out.LoadFortunePort;
import co.yapp.orbit.fortune.domain.Fortune;
import co.yapp.orbit.fortune.domain.FortuneItem;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LoadFortuneServiceTest {

    private LoadFortuneService loadFortuneService;
    private LoadFortunePort loadFortunePort;

    @BeforeEach
    void setUp() {
        loadFortunePort = Mockito.mock(LoadFortunePort.class);
        loadFortuneService = new LoadFortuneService(loadFortunePort);
    }

    @Test
    @DisplayName("운세 조회 요청이 들어오면, id에 해당하는 Fortune 객체를 반환한다.")
    void loadFortune() {
        // given
        Long fortuneId = 1L;
        Fortune fortune = Fortune.create(
            fortuneId,
            "Today is a great day!",
            new FortuneItem(90, "Success in studies"),
            new FortuneItem(90, "Prosperity in finance"),
            new FortuneItem(90, "Strong physical health"),
            new FortuneItem(90, "Harmonious love life"),
            "Red Shirt",
            "Black Pants",
            "White Sneakers",
            "Gold Necklace",
            "Green",
            "Blue",
            "Pizza"
        );

        when(loadFortunePort.findById(fortuneId)).thenReturn(Optional.of(fortune));

        // when
        Fortune findFortune = loadFortuneService.loadFortune(fortuneId);

        // then
        assertThat(findFortune).isEqualTo(fortune);
    }

    @Test
    @DisplayName("존재하지 않는 운세라면 FortuneNotFoundException이 발생한다.")
    void loadFortune_whenNotFound_thenThrowException() {
        Long fortuneId = 1L;
        when(loadFortunePort.findById(fortuneId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loadFortuneService.loadFortune(fortuneId))
            .isInstanceOf(FortuneNotFoundException.class);
    }
}