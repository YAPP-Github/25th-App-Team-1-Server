package co.yapp.orbit.fortune.application.port.out;

import co.yapp.orbit.fortune.domain.Fortune;

public interface SaveFortunePort {
    Long save(Fortune fortune);
}
