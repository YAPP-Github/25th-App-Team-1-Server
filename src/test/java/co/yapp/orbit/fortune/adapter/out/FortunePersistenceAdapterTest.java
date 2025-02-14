package co.yapp.orbit.fortune.adapter.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import co.yapp.orbit.fortune.domain.Fortune;
import co.yapp.orbit.fortune.domain.FortuneItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
class FortunePersistenceAdapterTest {

    @Autowired
    private FortuneRepository fortuneRepository;

    @Autowired
    private FortunePersistenceAdapter fortunePersistenceAdapter;

    @AfterEach
    void tearDown() {
        fortuneRepository.deleteAll();
    }

    @Test
    @DisplayName("Fortune 도메인 객체를 DB에 저장하고 id를 반환한다.")
    void save() {
        // given
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

        // when
        Long fortuneId = fortunePersistenceAdapter.save(fortune);

        // then
        assertThat(fortuneId).isNotNull();

        boolean exist = fortuneRepository.existsById(fortuneId);
        assertThat(exist).isTrue();

    }

    @Test
    @DisplayName("요청한 id에 해당하는 Fortune 도메인 객체를 반환한다.")
    void findById() {
        // given
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
        Long fortuneId = fortunePersistenceAdapter.save(fortune);
        
        // when
        Fortune findFortune = fortunePersistenceAdapter.findById(fortuneId).orElse(null);

        // then
        assertThat(findFortune).isNotNull();
        assertThat(findFortune.getId()).isEqualTo(fortuneId);
    }
}