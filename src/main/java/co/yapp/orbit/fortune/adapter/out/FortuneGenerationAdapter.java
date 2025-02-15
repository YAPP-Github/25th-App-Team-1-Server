package co.yapp.orbit.fortune.adapter.out;

import co.yapp.orbit.fortune.adapter.out.exception.FortuneParsingException;
import co.yapp.orbit.fortune.adapter.out.exception.WebClientFetchException;
import co.yapp.orbit.fortune.adapter.out.exception.FortunePromptLoadException;
import co.yapp.orbit.fortune.adapter.out.request.CreateFortuneRequest;
import co.yapp.orbit.fortune.adapter.out.request.FortuneGenerationRequest;
import co.yapp.orbit.fortune.adapter.out.response.CreateFortuneResponse;
import co.yapp.orbit.fortune.adapter.out.response.CreateFortuneResponse.FortuneItemResponse;
import co.yapp.orbit.fortune.adapter.out.response.FortuneGenerationResponse;
import co.yapp.orbit.fortune.application.port.out.FortuneGenerationPort;
import co.yapp.orbit.fortune.domain.Fortune;
import co.yapp.orbit.fortune.domain.FortuneItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
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
    private final ObjectMapper objectMapper;

    private static final String PROMPT_FILE_PATH = "templates/prompts/fortune_prompt_v2.json";
    private final JsonNode promptTemplate;

    public FortuneGenerationAdapter(
        @Value("${gemini.api.url}") String geminiApiUrl,
        @Value("${gemini.api.key}") String geminiApiKey,
        ObjectMapper objectMapper
    ) {
        this.webClient = WebClient.create();
        this.geminiFullUrl = geminiApiUrl + "?key=" + geminiApiKey;
        this.objectMapper = objectMapper;
        this.promptTemplate = loadPromptTemplate();
    }

    private JsonNode loadPromptTemplate() {
        try (InputStream inputStream = new ClassPathResource(PROMPT_FILE_PATH).getInputStream()) {
            return objectMapper.readTree(inputStream);
        } catch (IOException e) {
            log.error("프롬프트 템플릿 로드 오류: {}", PROMPT_FILE_PATH, e);
            throw new FortunePromptLoadException("프롬프트 템플릿을 불러오는 중 오류가 발생했습니다.");
        }
    }

    @Override
    public Fortune loadFortune(CreateFortuneRequest request) {
        String prompt = generatePrompt(request);
        String response = callAi(prompt);

        if (response == null || response.trim().isEmpty()) {
            log.error("WebClient 요청 오류: {}", request);
            throw new WebClientFetchException("운세 데이터를 불러오는 데 실패했습니다.");
        }

        return parseStringToFortune(response);
    }

    public String callAi(String prompt) {
        try {
            FortuneGenerationResponse response = webClient.post()
                .uri(geminiFullUrl)
                .bodyValue(new FortuneGenerationRequest(prompt))
                .retrieve()
                .bodyToMono(FortuneGenerationResponse.class)
                .block();

            return getFirstContentText(response);
        } catch (RuntimeException e) {
            log.error("WebClient 요청 오류: {}", prompt, e);
            throw new WebClientFetchException("운세 데이터 요청 중 오류가 발생했습니다.");
        }
    }

    private String getFirstContentText(FortuneGenerationResponse response) {
        return response.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

    public String generatePrompt(CreateFortuneRequest request) {
        try {
            ObjectNode userInfoJson = objectMapper.createObjectNode();
            userInfoJson.put("name", request.getName());
            userInfoJson.put("birth_date", request.getBirthDate());
            userInfoJson.put("birth_time", request.getBirthTime());
            userInfoJson.put("calendar_type", request.getCalendarType());
            userInfoJson.put("gender", request.getGender());

            ObjectNode copiedPrompt = promptTemplate.deepCopy();
            copiedPrompt.set("user_info", userInfoJson);

            ObjectNode todayDateJson = objectMapper.createObjectNode();
            todayDateJson.put("today_date", LocalDate.now().toString());
            copiedPrompt.set("today_date", todayDateJson);

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(copiedPrompt);
        } catch (JsonProcessingException e) {
            log.error("프롬프트 템플릿 작성 오류: {}", request, e);
            throw new FortunePromptLoadException("프롬프트를 작성하는 중 오류가 발생했습니다.");
        }
    }

    private Fortune parseStringToFortune(String response) {
        try {
            response = response
                .replaceAll("\\s+", " ")
                .replaceAll("\\n+", "\n")
                .replaceAll("\\\\+", "\\\\")
                .replaceAll("```", "")
                .replaceAll("json", "")
                .trim();

            CreateFortuneResponse fortuneResponse = objectMapper.readValue(response, CreateFortuneResponse.class);

            FortuneItemResponse studyCareer = fortuneResponse.getFortune().get("study_career");
            FortuneItemResponse wealth = fortuneResponse.getFortune().get("wealth");
            FortuneItemResponse health = fortuneResponse.getFortune().get("health");
            FortuneItemResponse love = fortuneResponse.getFortune().get("love");

            return Fortune.create(
                null,
                fortuneResponse.getDailyFortuneTitle(),
                fortuneResponse.getDailyFortuneDescription(),
                new FortuneItem(studyCareer.getScore(), studyCareer.getTitle(), studyCareer.getDescription()),
                new FortuneItem(wealth.getScore(), wealth.getTitle(), wealth.getDescription()),
                new FortuneItem(health.getScore(), health.getTitle(), health.getDescription()),
                new FortuneItem(love.getScore(), love.getTitle(), love.getDescription()),
                fortuneResponse.getLuckyOutfit().getTop(),
                fortuneResponse.getLuckyOutfit().getBottom(),
                fortuneResponse.getLuckyOutfit().getShoes(),
                fortuneResponse.getLuckyOutfit().getAccessory(),
                fortuneResponse.getUnluckyColor(),
                fortuneResponse.getLuckyColor(),
                fortuneResponse.getLuckyFood()
            );

        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", response, e);
            throw new FortuneParsingException("운세 데이터를 처리하는 과정에서 오류가 발생했습니다.");
        }
    }
}
