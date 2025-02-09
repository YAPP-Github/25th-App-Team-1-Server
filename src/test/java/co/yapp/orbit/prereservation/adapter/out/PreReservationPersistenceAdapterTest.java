package co.yapp.orbit.prereservation.adapter.out;

import co.yapp.orbit.prereservation.domain.PreReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"discord.webhook.url=http://dummy-url.com"})
class PreReservationPersistenceAdapterTest {

    @Autowired
    private PreReservationRepository preReservationRepository;

    @Autowired
    private PreReservationPersistenceAdapter preReservationPersistenceAdapter;

    @BeforeEach
    void setUp() {
        preReservationRepository.deleteAll();
    }

    @Test
    @DisplayName("중복 존재 체크 - 존재하지 않으면 false 리턴")
    void existsByEmailAndPhoneNumber_whenNotExist_returnFalse() {
        // given
        String email = "byungwook-min@naver.com";
        String phone = "010-1234-5678";

        // when
        boolean result = preReservationPersistenceAdapter.existsByEmailAndPhoneNumber(email, phone);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("중복 존재 체크 - 존재하면 true 리턴")
    void existsByEmailAndPhoneNumber_whenExist_returnTrue() {
        // given
        String email = "byungwook-min@naver.com";
        String phone = "010-1234-5678";
        PreReservationEntity entity = new PreReservationEntity(email, phone);
        preReservationRepository.save(entity);

        // when
        boolean result = preReservationPersistenceAdapter.existsByEmailAndPhoneNumber(email, phone);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("PreReservation 도메인 객체를 DB에 정상적으로 저장")
    void save() {
        // given
        PreReservation domain = new PreReservation("byungwook-min@naver.com", "010-1234-5678");

        // when
        preReservationPersistenceAdapter.save(domain);

        // then
        boolean exists = preReservationRepository.existsByEmailAndPhoneNumber("byungwook-min@naver.com", "010-1234-5678");
        assertThat(exists).isTrue();
    }
}