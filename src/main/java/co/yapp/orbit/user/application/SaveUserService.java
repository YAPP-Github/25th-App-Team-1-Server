package co.yapp.orbit.user.application;

import co.yapp.orbit.user.application.port.in.SaveUserUseCase;
import co.yapp.orbit.user.application.port.out.SaveUserPort;
import org.springframework.stereotype.Service;

@Service
public class SaveUserService implements SaveUserUseCase {

    private final SaveUserPort saveUserPort;

    public SaveUserService(SaveUserPort saveUserPort) {
        this.saveUserPort = saveUserPort;
    }
}
