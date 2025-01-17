package co.yapp.orbit.user.application;

import co.yapp.orbit.user.application.port.in.LoadUserUseCase;
import co.yapp.orbit.user.application.port.out.LoadUserPort;
import org.springframework.stereotype.Service;

@Service
public class LoadUserService implements LoadUserUseCase {

    private final LoadUserPort loadUserPort;

    public LoadUserService(LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }
}
