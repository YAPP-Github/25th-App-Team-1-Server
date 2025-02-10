package co.yapp.orbit.fortune.application;

import co.yapp.orbit.fortune.application.exception.FortuneNotFoundException;
import co.yapp.orbit.fortune.application.port.in.LoadFortuneUseCase;
import co.yapp.orbit.fortune.application.port.out.LoadFortunePort;
import co.yapp.orbit.fortune.domain.Fortune;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoadFortuneService implements LoadFortuneUseCase {

    private final LoadFortunePort loadFortunePort;

    public LoadFortuneService(LoadFortunePort loadFortunePort) {
        this.loadFortunePort = loadFortunePort;
    }

    @Override
    @Transactional(readOnly = true)
    public Fortune loadFortune(Long id) {
        return loadFortunePort.findById(id)
            .orElseThrow(() -> new FortuneNotFoundException("해당 운세를 찾을 수 없습니다. 운세 생성을 요청해주세요."));
    }
}
