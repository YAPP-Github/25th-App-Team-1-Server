package co.yapp.orbit.user.adapter.out;

import co.yapp.orbit.user.application.port.out.LoadUserPort;
import co.yapp.orbit.user.application.port.out.SaveUserPort;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements LoadUserPort, SaveUserPort {

    private final UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}