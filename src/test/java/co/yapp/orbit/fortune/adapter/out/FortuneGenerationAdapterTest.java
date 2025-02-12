package co.yapp.orbit.fortune.adapter.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import co.yapp.orbit.fortune.adapter.out.request.CreateFortuneRequest;
import co.yapp.orbit.user.domain.CalendarType;
import co.yapp.orbit.user.domain.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class FortuneGenerationAdapterTest {

    @Autowired
    private FortuneGenerationAdapter fortuneGenerationAdapter;

    @Autowired
    private FortuneRepository fortuneRepository;

    @Test
    @DisplayName("운세 프롬프트가 올바르게 생성된다.")
    void generatePrompt_isCorrectlyGenerated() {
        // given
        CreateFortuneRequest request = new CreateFortuneRequest(
            "John", "1990-01-01", "12:00", CalendarType.SOLAR.name(), Gender.MALE.name());

        // when
        String prompt = fortuneGenerationAdapter.generatePrompt(request);

        // then
        assertThat(prompt).contains("John");
        assertThat(prompt).contains("1990-01-01");
        assertThat(prompt).contains("12:00");
    }
}