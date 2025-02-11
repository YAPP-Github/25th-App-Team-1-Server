package co.yapp.orbit.user.application.port.out;

import co.yapp.orbit.user.domain.User;
import java.util.Optional;

public interface LoadUserPort {
    Optional<User> findById(Long id);
}
