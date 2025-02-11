package co.yapp.orbit.user.adapter.out;

import co.yapp.orbit.user.application.port.out.LoadUserPort;
import co.yapp.orbit.user.application.port.out.SaveUserPort;
import co.yapp.orbit.user.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements SaveUserPort, LoadUserPort {

    private final UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long save(User user) {
        UserEntity entity = new UserEntity(
            user.getName(),
            user.getBirthDate(),
            user.getBirthTime(),
            user.getCalendarType(),
            user.getGender()
        );
        UserEntity savedEntity = userRepository.save(entity);
        return savedEntity.getId();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
            .map(entity -> new User(
                entity.getId(),
                entity.getName(),
                entity.getBirthDate(),
                entity.getBirthTime(),
                entity.getCalendarType(),
                entity.getGender()
            ));
    }
}
