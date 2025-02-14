package co.yapp.orbit.user.adapter.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import co.yapp.orbit.user.application.exception.UserNotFoundException;
import co.yapp.orbit.user.domain.CalendarType;
import co.yapp.orbit.user.domain.Gender;
import co.yapp.orbit.user.domain.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class UserPersistenceAdapterTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPersistenceAdapter userPersistenceAdapter;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("UserPersistenceAdapter의 save 메서드가 도메인 객체를 DB에 저장하고 id를 반환한다.")
    void save() {
        User user = new User(null, "홍길동", LocalDate.parse("2025-02-09"), LocalTime.parse("08:30:00"), CalendarType.SOLAR, Gender.MALE);
        Long userId = userPersistenceAdapter.save(user);
        assertThat(userId).isNotNull();
        boolean exists = userRepository.findById(userId).isPresent();
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("UserPersistenceAdapter의 findById 메서드가 DB에서 사용자 정보를 조회한다.")
    void findById() {
        // given
        User user = new User(null, "홍길동",
            LocalDate.parse("2025-02-09"),
            LocalTime.parse("08:30:00"),
            CalendarType.SOLAR,
            Gender.MALE);
        Long userId = userPersistenceAdapter.save(user);

        // when
        Optional<User> optionalUser = userPersistenceAdapter.findById(userId);

        // then
        assertThat(optionalUser).isPresent();
        User foundUser = optionalUser.get();
        assertThat(foundUser.getId()).isEqualTo(userId);
        assertThat(foundUser.getName()).isEqualTo("홍길동");
        assertThat(foundUser.getBirthDate()).isEqualTo(LocalDate.parse("2025-02-09"));
        assertThat(foundUser.getBirthTime()).isEqualTo(LocalTime.parse("08:30:00"));
        assertThat(foundUser.getCalendarType()).isEqualTo(CalendarType.SOLAR);
        assertThat(foundUser.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    @DisplayName("UserPersistenceAdapter의 update 메서드가 DB의 사용자 정보를 올바르게 업데이트 한다.")
    void update() {
        // given
        User user = new User(null, "홍길동",
            LocalDate.parse("2025-02-09"),
            LocalTime.parse("08:30:00"),
            CalendarType.SOLAR,
            Gender.MALE);
        Long userId = userPersistenceAdapter.save(user);

        // when
        User updatedUser = new User(userId, "김철수",
            LocalDate.parse("2025-03-10"),
            LocalTime.parse("09:00:00"),
            CalendarType.LUNAR,
            Gender.MALE);
        userPersistenceAdapter.update(updatedUser);

        // then
        Optional<User> optionalUser = userPersistenceAdapter.findById(userId);
        assertThat(optionalUser).isPresent();
        User foundUser = optionalUser.get();
        assertThat(foundUser.getName()).isEqualTo("김철수");
        assertThat(foundUser.getBirthDate()).isEqualTo(LocalDate.parse("2025-03-10"));
        assertThat(foundUser.getBirthTime()).isEqualTo(LocalTime.parse("09:00:00"));
        assertThat(foundUser.getCalendarType()).isEqualTo(CalendarType.LUNAR);
        assertThat(foundUser.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    @DisplayName("UserPersistenceAdapter의 update 메서드가 존재하지 않는 사용자를 업데이트하려 할 때 예외를 발생시킨다.")
    void update_nonexistentUser_throwsException() {
        // given
        Long nonExistentId = 999L;
        User updatedUser = new User(nonExistentId, "김철수",
            LocalDate.parse("2025-03-10"),
            LocalTime.parse("09:00:00"),
            CalendarType.LUNAR,
            Gender.MALE);

        // when & then
        assertThrows(UserNotFoundException.class, () -> {
            userPersistenceAdapter.update(updatedUser);
        });
    }
}
