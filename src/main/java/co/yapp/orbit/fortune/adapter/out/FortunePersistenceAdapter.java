package co.yapp.orbit.fortune.adapter.out;

import co.yapp.orbit.fortune.application.port.out.LoadFortunePort;
import org.springframework.stereotype.Component;

@Component
public class FortunePersistenceAdapter implements LoadFortunePort {

    private final FortuneRepository fortuneRepository;

    public FortunePersistenceAdapter(FortuneRepository fortuneRepository) {
        this.fortuneRepository = fortuneRepository;
    }
}
