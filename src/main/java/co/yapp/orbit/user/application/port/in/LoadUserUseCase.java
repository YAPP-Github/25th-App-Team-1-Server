package co.yapp.orbit.user.application.port.in;

import co.yapp.orbit.user.domain.User;

public interface LoadUserUseCase {

    User loadUser(Long id);
}
