package co.yapp.orbit.user.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    @DisplayName("동일한 id, 이름, 생년월일, 태어난시간, 성별을 가진 객체는 equals()가 true를 반환해야 한다.")
    void equals_sameAttributes_returnsTrue() {
        Long id = 1L;
        String name = "홍길동";
        LocalDate birthDate = LocalDate.parse("2025-02-09");
        LocalTime birthTime = LocalTime.parse("08:30:00");
        CalendarType calendarType = CalendarType.SOLAR;
        Gender gender = Gender.MALE;

        User user1 = new User(id, name, birthDate, birthTime, calendarType, gender);
        User user2 = new User(id, name, birthDate, birthTime, calendarType, gender);

        assertThat(user1)
            .isEqualTo(user2)
            .hasSameHashCodeAs(user2);
    }

    @Test
    @DisplayName("서로 다른 id를 가진 객체는 equals()가 false를 반환해야 한다.")
    void equals_differentId_returnsFalse() {
        String name = "홍길동";
        LocalDate birthDate = LocalDate.parse("2025-02-09");
        LocalTime birthTime = LocalTime.parse("08:30:00");
        Gender gender = Gender.MALE;
        CalendarType calendarType = CalendarType.SOLAR;

        User user1 = new User(1L, name, birthDate, birthTime, calendarType, gender);
        User user2 = new User(2L, name, birthDate, birthTime, calendarType, gender);

        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    @DisplayName("서로 다른 이름을 가진 객체는 equals()가 false를 반환해야 한다.")
    void equals_differentName_returnsFalse() {
        Long id = 1L;
        LocalDate birthDate = LocalDate.parse("2025-02-09");
        LocalTime birthTime = LocalTime.parse("08:30:00");
        CalendarType calendarType = CalendarType.SOLAR;
        Gender gender = Gender.MALE;

        User user1 = new User(id, "홍길동", birthDate, birthTime, calendarType, gender);
        User user2 = new User(id, "김철수", birthDate, birthTime, calendarType, gender);

        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    @DisplayName("서로 다른 생년월일을 가진 객체는 equals()가 false를 반환해야 한다.")
    void equals_differentBirthDate_returnsFalse() {
        Long id = 1L;
        String name = "홍길동";
        LocalDate birthDate1 = LocalDate.parse("2025-02-09");
        LocalDate birthDate2 = LocalDate.parse("2024-01-01");
        LocalTime birthTime = LocalTime.parse("08:30:00");
        CalendarType calendarType = CalendarType.SOLAR;
        Gender gender = Gender.MALE;

        User user1 = new User(id, name, birthDate1, birthTime, calendarType, gender);
        User user2 = new User(id, name, birthDate2, birthTime, calendarType, gender);

        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    @DisplayName("서로 다른 태어난시간을 가진 객체는 equals()가 false를 반환해야 한다.")
    void equals_differentBirthTime_returnsFalse() {
        Long id = 1L;
        String name = "홍길동";
        LocalDate birthDate = LocalDate.parse("2025-02-09");
        LocalTime birthTime1 = LocalTime.parse("08:30:00");
        LocalTime birthTime2 = LocalTime.parse("09:00:00");
        CalendarType calendarType = CalendarType.SOLAR;
        Gender gender = Gender.MALE;

        User user1 = new User(id, name, birthDate, birthTime1, calendarType, gender);
        User user2 = new User(id, name, birthDate, birthTime2, calendarType, gender);

        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    @DisplayName("서로 다른 성별을 가진 객체는 equals()가 false를 반환해야 한다.")
    void equals_differentGender_returnsFalse() {
        Long id = 1L;
        String name = "홍길동";
        LocalDate birthDate = LocalDate.parse("2025-02-09");
        LocalTime birthTime = LocalTime.parse("08:30:00");
        CalendarType calendarType = CalendarType.SOLAR;

        User user1 = new User(id, name, birthDate, birthTime, calendarType, Gender.MALE);
        User user2 = new User(id, name, birthDate, birthTime, calendarType, Gender.FEMALE);

        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    @DisplayName("자기 자신과의 equals()는 true를 반환해야 한다.")
    void equals_itself_returnsTrue() {
        User user = new User(1L, "홍길동", LocalDate.parse("2025-02-09"), LocalTime.parse("08:30:00"), CalendarType.SOLAR, Gender.MALE);
        assertThat(user).isEqualTo(user);
    }

    @Test
    @DisplayName("다른 타입의 객체와 equals() 비교 시 false를 반환해야 한다.")
    void equals_differentType_returnsFalse() {
        User user = new User(1L, "홍길동", LocalDate.parse("2025-02-09"), LocalTime.parse("08:30:00"), CalendarType.SOLAR, Gender.MALE);
        String other = "some string";
        assertThat(user).isNotEqualTo(other);
    }
}
