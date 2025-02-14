package co.yapp.orbit.user.application.port.out;

import co.yapp.orbit.user.domain.User;

public interface UpdateUserPort {
    void update(User user);
}
