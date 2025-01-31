package co.yapp.orbit.prereservation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PreReservationTest {

    @Test
    @DisplayName("동일한 이름과 전화번호를 가진 객체는 equals()가 true를 반환해야 한다.")
    void equals_sameNameAndPhoneNumber_returnsTrue() {
        // given
        PreReservation preReservation1 = new PreReservation("홍길동", "010-1234-5678");
        PreReservation preReservation2 = new PreReservation("홍길동", "010-1234-5678");

        // when & then
        assertThat(preReservation1).hasSameHashCodeAs(preReservation2);
    }

    @Test
    @DisplayName("이름이 다르면 equals()가 false를 반환해야 한다.")
    void equals_differentName_returnsFalse() {
        // given
        PreReservation preReservation1 = new PreReservation("홍길동", "010-1234-5678");
        PreReservation preReservation2 = new PreReservation("이순신", "010-1234-5678");

        // when & then
        assertThat(preReservation1).isNotEqualTo(preReservation2);
    }

    @Test
    @DisplayName("전화번호가 다르면 equals()가 false를 반환해야 한다.")
    void equals_differentPhoneNumber_returnsFalse() {
        // given
        PreReservation preReservation1 = new PreReservation("홍길동", "010-1234-5678");
        PreReservation preReservation2 = new PreReservation("홍길동", "010-9876-5432");

        // when & then
        assertThat(preReservation1).isNotEqualTo(preReservation2);
    }

    @Test
    @DisplayName("자기 자신과는 equals()가 true여야 한다.")
    void equals_itself_returnsTrue() {
        // given
        PreReservation preReservation = new PreReservation("홍길동", "010-1234-5678");

        // when & then
        assertThat(preReservation).isEqualTo(preReservation);
    }

    @Test
    @DisplayName("다른 타입의 객체와는 equals()가 false여야 한다.")
    void equals_differentType_returnsFalse() {
        // given
        PreReservation preReservation = new PreReservation("홍길동", "010-1234-5678");
        String otherObject = "홍길동";

        // when & then
        assertThat(preReservation).isNotEqualTo(otherObject);
    }
}