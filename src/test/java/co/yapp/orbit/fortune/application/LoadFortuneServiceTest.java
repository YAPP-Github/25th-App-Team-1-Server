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
            1L,
            "윤다혜, 오늘은 기분 좋은 일들이 많을 거야!",
            "긍정적인 마음으로 하루를 시작하면 좋은 결과가 있을 거 같아. 주변 사람들과의 관계도 원만하게 유지하면서 행복한 하루 보내길 바라!",
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