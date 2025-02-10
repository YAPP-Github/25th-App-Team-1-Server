package co.yapp.orbit.fortune.application.port.out;

import co.yapp.orbit.fortune.domain.Fortune;
import java.util.Optional;

public interface LoadFortunePort {
    Optional<Fortune> findById(Long id);
}
