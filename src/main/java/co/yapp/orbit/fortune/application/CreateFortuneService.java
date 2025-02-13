package co.yapp.orbit.fortune.application;

import co.yapp.orbit.fortune.adapter.out.request.CreateFortuneRequest;
import co.yapp.orbit.fortune.application.exception.FortuneCreateInvalidUserException;
import co.yapp.orbit.fortune.application.port.in.CreateFortuneCommand;
import co.yapp.orbit.fortune.application.port.in.CreateFortuneUseCase;
import co.yapp.orbit.fortune.application.port.out.FortuneGenerationPort;
import co.yapp.orbit.fortune.application.port.out.SaveFortunePort;
import co.yapp.orbit.fortune.domain.Fortune;
import co.yapp.orbit.user.adapter.out.response.UserInfoResponse;
import co.yapp.orbit.user.application.exception.UserNotFoundException;
import co.yapp.orbit.user.application.port.out.UserApiPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CreateFortuneService implements CreateFortuneUseCase {

    private final FortuneGenerationPort fortuneGenerationPort;
    private final SaveFortunePort saveFortunePort;
    private final UserApiPort userApiPort;

    public CreateFortuneService(FortuneGenerationPort fortuneGenerationPort,
        SaveFortunePort saveFortunePort, UserApiPort userApiPort) {
        this.fortuneGenerationPort = fortuneGenerationPort;
        this.saveFortunePort = saveFortunePort;
        this.userApiPort = userApiPort;
    }

    @Override
    @Transactional
    public Fortune createFortune(CreateFortuneCommand command) {
        CreateFortuneRequest request = null;

        try {
            UserInfoResponse userInfo = userApiPort.getUserInfo(command.getUserId());
            request = new CreateFortuneRequest(
                userInfo.getName(),
                userInfo.getBirthDate(),
                userInfo.getBirthTime(),
                userInfo.getCalendarType(),
                userInfo.getGender());

        } catch (UserNotFoundException e) {
            log.error("운세 생성 중 UserNotFoundException 발생: {}", command.getUserId(), e);
            throw new FortuneCreateInvalidUserException("존재하지 않는 사용자입니다.");
        }

        Fortune fortune = fortuneGenerationPort.loadFortune(request);

        Long fortuneId = saveFortunePort.save(fortune);

        return Fortune.create(
            fortuneId,
            fortune.getDailyFortune(),
            fortune.getStudyCareerFortune(),
            fortune.getWealthFortune(),
            fortune.getHealthFortune(),
            fortune.getLoveFortune(),
            fortune.getLuckyOutfitTop(),
            fortune.getLuckyOutfitBottom(),
            fortune.getLuckyOutfitShoes(),
            fortune.getLuckyOutfitAccessory(),
            fortune.getUnluckyColor(),
            fortune.getLuckyColor(),
            fortune.getLuckyFood()
        );
    }
}
