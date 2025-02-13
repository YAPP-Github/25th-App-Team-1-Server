package co.yapp.orbit.user.adapter.out;

import co.yapp.orbit.user.adapter.out.response.UserInfoResponse;
import co.yapp.orbit.user.application.exception.UserNotFoundException;
import co.yapp.orbit.user.application.port.out.UserApiPort;
import org.springframework.stereotype.Component;

@Component
public class UserIntegrationAdapter implements UserApiPort {

    private final UserRepository userRepository;

    public UserIntegrationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        return userRepository.findById(userId)
            .map(entity -> new UserInfoResponse(
                entity.getName(),
                entity.getBirthDate().toString(),
                entity.getBirthTime().toString(),
                entity.getCalendarType().toString(),
                entity.getGender().name()
            ))
            .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. id: " + userId));
    }
}
