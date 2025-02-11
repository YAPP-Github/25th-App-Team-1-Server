package co.yapp.orbit.user.application;

import co.yapp.orbit.user.application.exception.UserNotFoundException;
import co.yapp.orbit.user.application.port.in.LoadUserUseCase;
import co.yapp.orbit.user.application.port.out.LoadUserPort;
import co.yapp.orbit.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoadUserService implements LoadUserUseCase {

    private final LoadUserPort loadUserPort;

    public LoadUserService(LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Transactional(readOnly = true)
    @Override
    public User loadUser(Long id) {
        return loadUserPort.findById(id)
            .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. id: " + id));
    }
}
