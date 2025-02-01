package co.yapp.orbit.prereservation.adapter.out;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PreReservationRepositoryTest {

    @Autowired
    private PreReservationRepository preReservationRepository;

    @Test
    @DisplayName("existsByNameAndPhoneNumber 정상 동작 테스트")
    void testExistsByNameAndPhoneNumber() {
        // given
        PreReservationEntity entity = new PreReservationEntity("홍길동", "010-1234-5678");
        preReservationRepository.save(entity);

        // when
        boolean exists = preReservationRepository.existsByEmailAndPhoneNumber("홍길동", "010-1234-5678");

        // then
        assertThat(exists).isTrue();
    }
}