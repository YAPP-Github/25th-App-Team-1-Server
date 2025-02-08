package co.yapp.orbit.fortune.application.port.in;

import co.yapp.orbit.fortune.domain.Fortune;

public interface CreateFortuneUseCase {
    Fortune createFortune(LoadFortuneCommand command);
}
