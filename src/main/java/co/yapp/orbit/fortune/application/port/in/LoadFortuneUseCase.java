package co.yapp.orbit.fortune.application.port.in;

import co.yapp.orbit.fortune.domain.Fortune;

public interface LoadFortuneUseCase {
    Fortune loadFortune(Long id);
}
