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
}
