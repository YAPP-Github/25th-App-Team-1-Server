package co.yapp.orbit.user.adapter.out;

import static org.assertj.core.api.Assertions.assertThat;

import co.yapp.orbit.user.domain.CalendarType;
import co.yapp.orbit.user.domain.Gender;
import co.yapp.orbit.user.domain.User;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"discord.webhook.url=http://dummy-url.com"})
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
    @DisplayName("UserPersistenceAdapter의 findById 메서드가 도메인 객체를 올바르게 반환한다.")
    void findById() {
        // given
        UserEntity entity = new UserEntity("홍길동",
        LocalDate.parse("2025-02-09"), LocalTime.parse("08:30:00"), CalendarType.SOLAR, Gender.MALE);
        UserEntity savedEntity = userRepository.save(entity);
        Long userId = savedEntity.getId();

        // when
        User user = userPersistenceAdapter.findById(userId).orElse(null);

        // then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getName()).isEqualTo("홍길동");
        assertThat(user.getBirthDate()).isEqualTo(LocalDate.parse("2025-02-09"));
        assertThat(user.getBirthTime()).isEqualTo(LocalTime.parse("08:30:00"));
        assertThat(user.getGender()).isEqualTo(Gender.MALE);
    }
}
