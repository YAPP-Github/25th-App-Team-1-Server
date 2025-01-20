package co.yapp.orbit.fortune.application;

import co.yapp.orbit.fortune.application.port.in.LoadFortuneUseCase;
import co.yapp.orbit.fortune.application.port.out.LoadFortunePort;
import org.springframework.stereotype.Service;

@Service
public class FortuneService implements LoadFortuneUseCase {

    private final LoadFortunePort loadFortunePort;

    public FortuneService(LoadFortunePort loadFortunePort) {
        this.loadFortunePort = loadFortunePort;
    }
}
