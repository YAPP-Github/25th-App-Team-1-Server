package co.yapp.orbit.fortune.application.port.out;

import co.yapp.orbit.fortune.adapter.out.request.CreateFortuneRequest;
import co.yapp.orbit.fortune.domain.Fortune;

public interface FortuneGenerationPort {
    Fortune loadFortune(CreateFortuneRequest request);
}
