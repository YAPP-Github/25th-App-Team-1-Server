package co.yapp.orbit.fortune.adapter.out;

import co.yapp.orbit.fortune.adapter.out.exception.FortuneFetchException;
import co.yapp.orbit.fortune.adapter.out.exception.FortunePromptLoadException;
import co.yapp.orbit.fortune.adapter.out.request.CreateFortuneRequest;
import co.yapp.orbit.fortune.adapter.out.request.FortuneGenerationRequest;
import co.yapp.orbit.fortune.adapter.out.response.FortuneGenerationResponse;
import co.yapp.orbit.fortune.application.exception.FortuneParsingException;
import co.yapp.orbit.fortune.application.port.out.FortuneGenerationPort;
import co.yapp.orbit.fortune.domain.Fortune;
import co.yapp.orbit.fortune.domain.FortuneItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class FortuneGenerationAdapter implements FortuneGenerationPort {

    private final WebClient webClient;
    private final String geminiFullUrl;

    private static final String PROMPT_FILE_PATH = "templates/prompts/fortune_prompt_v1.json";

    public FortuneGenerationAdapter(
        @Value("${gemini.api.url}") String geminiApiUrl,
        @Value("${gemini.api.key}") String geminiApiKey
    ) {
        this.webClient = WebClient.create();
        this.geminiFullUrl = geminiApiUrl + "?key=" + geminiApiKey;
    }

    @Override
    public Fortune loadFortune(CreateFortuneRequest request) {
        String prompt = generatePrompt(request);

        try {
            String response = callAi(prompt);

            if (response == null || response.trim().isEmpty()) {
                throw new FortuneFetchException("운세 데이터를 불러오는 데 실패했습니다.");
            }

            response = response
                .replaceAll("\\s+", " ")
                .replaceAll("\\n+", "\n")
                .replaceAll("\\\\+", "\\\\")
                .replaceAll("```", "")
                .replaceAll("json", "")
                .trim();

            return parseStringToFortune(response);
        } catch (Exception e) {
            throw new FortuneFetchException("운세 데이터를 불러오는 중 오류가 발생했습니다.");
        }
    }

    public String callAi(String prompt) {
        FortuneGenerationResponse response = webClient.post()
            .uri(geminiFullUrl)
            .bodyValue(new FortuneGenerationRequest(prompt))
            .retrieve()
            .bodyToMono(FortuneGenerationResponse.class)
            .block();

        return response.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

    public String generatePrompt(CreateFortuneRequest request) {
        try (InputStream inputStream = new ClassPathResource(PROMPT_FILE_PATH).getInputStream()) {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode promptJson = objectMapper.readTree(inputStream);

            ObjectNode userInfoJson = objectMapper.createObjectNode();
            userInfoJson.put("name", request.getName());
            userInfoJson.put("birth_date", request.getBirthDate());
            userInfoJson.put("birth_time", request.getBirthTime());
            userInfoJson.put("calendar_type", request.getCalendarType());
            userInfoJson.put("gender", request.getGender());

            ((ObjectNode) promptJson).set("user_info", userInfoJson);

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(promptJson);

        } catch (IOException e) {
            log.error("JSON 파싱 오류: {}", request.toString(), e);
            throw new FortunePromptLoadException("프롬프트를 작성하는 중 오류가 발생했습니다.");
        }
    }

    private Fortune parseStringToFortune(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            String dailyFortune = rootNode.path("daily_fortune").asText();

            JsonNode fortuneNode = rootNode.path("fortune");

            FortuneItem studyCareerFortune = new FortuneItem(
                fortuneNode.path("study_career").path("score").asInt(),
                fortuneNode.path("study_career").path("title").asText(),
                fortuneNode.path("study_career").path("description").asText()
            );
            FortuneItem wealthFortune = new FortuneItem(
                fortuneNode.path("wealth").path("score").asInt(),
                fortuneNode.path("wealth").path("title").asText(),
                fortuneNode.path("wealth").path("description").asText()
            );
            FortuneItem healthFortune = new FortuneItem(
                fortuneNode.path("health").path("score").asInt(),
                fortuneNode.path("health").path("title").asText(),
                fortuneNode.path("health").path("description").asText()
            );
            FortuneItem loveFortune = new FortuneItem(
                fortuneNode.path("love").path("score").asInt(),
                fortuneNode.path("love").path("title").asText(),
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
