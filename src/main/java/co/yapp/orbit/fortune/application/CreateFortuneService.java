package co.yapp.orbit.fortune.application;

import co.yapp.orbit.fortune.application.exception.FortuneParsingException;
import co.yapp.orbit.fortune.application.port.in.CreateFortuneCommand;
import co.yapp.orbit.fortune.application.port.in.CreateFortuneUseCase;
import co.yapp.orbit.fortune.application.port.out.FortuneGenerationPort;
import co.yapp.orbit.fortune.application.port.out.SaveFortunePort;
import co.yapp.orbit.fortune.domain.Fortune;
import co.yapp.orbit.fortune.domain.FortuneItem;
import co.yapp.orbit.user.application.port.out.LoadUserPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CreateFortuneService implements CreateFortuneUseCase {

    private final FortuneGenerationPort fortuneGenerationPort;
    private final SaveFortunePort saveFortunePort;
    private final LoadUserPort loadUserPort;

    public CreateFortuneService(FortuneGenerationPort fortuneGenerationPort, SaveFortunePort saveFortunePort, LoadUserPort loadUserPort) {
        this.fortuneGenerationPort = fortuneGenerationPort;
        this.saveFortunePort = saveFortunePort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    @Transactional
    public Fortune createFortune(CreateFortuneCommand command) {
        // TODO: 사용자 정보 프롬프트에 추가
        // User user = loadUserPort.findById(command.getUserId());
        // CreateFortuneRequest request = new CreateFortuneRequest(user.getName(), user.getBirthday(), user.getBirthTime(), user.getGender());

        String response = fortuneGenerationPort.loadFortune();
        Fortune fortune = parseStringToFortune(response);

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

    private Fortune parseStringToFortune(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            String dailyFortune = rootNode.path("daily_fortune").asText();

            JsonNode fortuneNode = rootNode.path("fortune");

            FortuneItem studyCareerFortune = new FortuneItem(
                fortuneNode.path("study_career").path("score").asInt(),
                fortuneNode.path("study_career").path("description").asText()
            );
            FortuneItem wealthFortune = new FortuneItem(
                fortuneNode.path("wealth").path("score").asInt(),
                fortuneNode.path("wealth").path("description").asText()
            );
            FortuneItem healthFortune = new FortuneItem(
                fortuneNode.path("health").path("score").asInt(),
                fortuneNode.path("health").path("description").asText()
            );
            FortuneItem loveFortune = new FortuneItem(
                fortuneNode.path("love").path("score").asInt(),
                fortuneNode.path("love").path("description").asText()
            );

            JsonNode luckyOutfitNode = rootNode.path("lucky_outfit");
            String luckyOutfitTop = luckyOutfitNode.path("top").asText();
            String luckyOutfitBottom = luckyOutfitNode.path("bottom").asText();
            String luckyOutfitShoes = luckyOutfitNode.path("shoes").asText();
            String luckyOutfitAccessory = luckyOutfitNode.path("accessory").asText();

            String unluckyColor = rootNode.path("unlucky_color").asText();
            String luckyColor = rootNode.path("lucky_color").asText();
            String luckyFood = rootNode.path("lucky_food").asText();

            return Fortune.create(null, dailyFortune, studyCareerFortune, wealthFortune,
                healthFortune, loveFortune, luckyOutfitTop, luckyOutfitBottom, luckyOutfitShoes,
                luckyOutfitAccessory, unluckyColor, luckyColor, luckyFood);

        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", response, e);
            throw new FortuneParsingException("운세 데이터를 처리하는 과정에서 오류가 발생했습니다.");
        }
    }
}
