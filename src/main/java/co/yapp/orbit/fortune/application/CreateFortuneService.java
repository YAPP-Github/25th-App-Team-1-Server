package co.yapp.orbit.fortune.application;

import co.yapp.orbit.fortune.application.exception.FortuneParsingException;
import co.yapp.orbit.fortune.application.port.in.CreateFortuneUseCase;
import co.yapp.orbit.fortune.application.port.in.LoadFortuneCommand;
import co.yapp.orbit.fortune.application.port.out.GeminiApiPort;
import co.yapp.orbit.fortune.application.port.out.SaveFortunePort;
import co.yapp.orbit.fortune.domain.Fortune;
import co.yapp.orbit.fortune.domain.FortuneItem;
import co.yapp.orbit.user.application.port.out.LoadUserPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CreateFortuneService implements CreateFortuneUseCase {

    private final GeminiApiPort geminiApiPort;
    private final SaveFortunePort saveFortunePort;
    private final LoadUserPort loadUserPort;

    public CreateFortuneService(GeminiApiPort geminiApiPort, SaveFortunePort saveFortunePort, LoadUserPort loadUserPort) {
        this.geminiApiPort = geminiApiPort;
        this.saveFortunePort = saveFortunePort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    @Transactional
    public Fortune createFortune(LoadFortuneCommand command) {
        // TODO: 사용자 정보 프롬프트에 추가
        // User user = loadUserPort.findById(command.userId());
        // CreateFortuneRequest request = new CreateFortuneRequest(user.getName(), user.getBirthday(), user.getBirthTime(), user.getGender());

        String response = geminiApiPort.loadFortune();
        Fortune fortune = parseStringToFortune(response);

        Long fortuneId = saveFortunePort.save(fortune);

        return Fortune.of(fortuneId, fortune);
    }

    private Fortune parseStringToFortune(String response) {
        try {
            response = response.replace("```", "").replace("json", "").trim();

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

            int avgFortuneScore = calcAvgFortuneScore(
                studyCareerFortune.getScore(),
                wealthFortune.getScore(),
                healthFortune.getScore(),
                loveFortune.getScore()
            );

            JsonNode luckyOutfitNode = rootNode.path("lucky_outfit");
            String luckyOutfitTop = luckyOutfitNode.path("top").asText();
            String luckyOutfitBottom = luckyOutfitNode.path("bottom").asText();
            String luckyOutfitShoes = luckyOutfitNode.path("shoes").asText();
            String luckyOutfitAccessory = luckyOutfitNode.path("accessory").asText();

            String unluckyColor = rootNode.path("unlucky_color").asText();
            String luckyColor = rootNode.path("lucky_color").asText();
            String luckyFood = rootNode.path("lucky_food").asText();

            return Fortune.of(dailyFortune, avgFortuneScore, studyCareerFortune, wealthFortune,
                healthFortune, loveFortune, luckyOutfitTop, luckyOutfitBottom, luckyOutfitShoes,
                luckyOutfitAccessory, unluckyColor, luckyColor, luckyFood);

        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", response, e);
            throw new FortuneParsingException("운세 데이터를 처리하는 과정에서 오류가 발생했습니다.");
        }
    }

    private int calcAvgFortuneScore(int... scores) {
        return (int) Arrays.stream(scores).average().orElse(0);
    }
}
