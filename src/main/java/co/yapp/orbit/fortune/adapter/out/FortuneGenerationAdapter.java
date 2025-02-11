package co.yapp.orbit.fortune.adapter.out;

import co.yapp.orbit.fortune.adapter.out.exception.FortuneFetchException;
import co.yapp.orbit.fortune.adapter.out.request.FortuneAiRequest;
import co.yapp.orbit.fortune.adapter.out.response.FortuneGenerationResponse;
import co.yapp.orbit.fortune.application.port.out.FortuneGenerationPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FortuneGenerationAdapter implements FortuneGenerationPort {

    private final WebClient webClient;
    private final String geminiFullUrl;

    public FortuneGenerationAdapter(
        @Value("${gemini.api.url}") String geminiApiUrl,
        @Value("${gemini.api.key}") String geminiApiKey
    ) {
        this.webClient = WebClient.create();
        this.geminiFullUrl = geminiApiUrl + "?key=" + geminiApiKey;
    }

    @Override
    public String loadFortune() {
        String prompt = generatePrompt();

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

            return response;
        } catch (Exception e) {
            throw new FortuneFetchException("운세 데이터를 불러오는 중 오류가 발생했습니다.");
        }
    }

    public String callAi(String prompt) {
        FortuneGenerationResponse response = webClient.post()
            .uri(geminiFullUrl)
            .bodyValue(new FortuneAiRequest(prompt))
            .retrieve()
            .bodyToMono(FortuneGenerationResponse.class)
            .block();

        return response.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

    // TODO: 사용자 정보(CreateFortuneRequest) 추가
    public String generatePrompt() {
        StringBuilder prompt = new StringBuilder();

        prompt.append("사용자 정보와 사주오행을 바탕으로 운세를 생성하세요(출력 형식 외의 문장 금지)\n" +
            "출력 형식(JSON 형태):\n" + "{" +
            "\"daily_fortune\": \"오늘 운세(55~68자,사주오행에 기반한 개인화된 내용)\",\n" +
            "\"fortune\": {" +
            "\"study_career\": {" +
            "\"score\": \"학업/직장운 점수\"," +
            "\"description\": \"학업/직장운에 대한 설명\"" +
            "}," +
            "\"wealth\": {" +
            "\"score\": \"재물운 점수\"," +
            "\"description\": \"재물운에 대한 설명, 주식/부동산/투자 등 구체적 상황을 예시로 설명\"" +
            "}," +
            "\"health\": {" +
            "\"score\": \"건강운 점수\"," +
            "\"description\": \"건강운에 대한 설명\"" +
            "}," +
            "\"love\": {" +
            "\"score\": \"애정운 점수\"," +
            "\"description\": \"애정운에 대한 설명, 커플/솔로 모두 적용\"" +
            "}" + "}," +
            "\"lucky_outfit\": {" +
            "\"top\": \"상의\"," +
            "\"bottom\": \"하의\"," +
            "\"shoes\": \"신발\"," +
            "\"accessory\": \"상의/하의/신발을 제외한 기타 코디 아이템(향/추상적 표현 제외)\"" +
            "}," +
            "\"unlucky_color\": \"불운 색상(1가지)\"," +
            "\"lucky_color\": \"행운 색상(1가지)\"," +
            "\"lucky_food\": \"추천 음식(1가지)\"" +
            "}" +
            "출력 조건:\n" +
            "1. name 외의 사용자 정보를 표시하지 않는다.\n" +
            "2. 귀엽고 친근한 반말 말투(인사와 애교 금지), 친구처럼 다정한 어투로 ‘~야’,‘~어’,‘~여’,‘~까?’를 사용해 말투를 부드럽게 조정.\n" +
            "3. 점수는 5단위로 표시하지 않는다. 모든 fortune의 점수는 숫자만 출력\n" +
            "4. 설명은 핵심 메시지(두괄식으로 짧고 명료하게)-설명 및 배경(이유나 영향을 사용자에게 제공)-행동 제안(구체적이고 실천 가능한 제안을 추가)의 구조를 따른다, 최대 70~80자, 랜덤하게 선택되도록 권유형, 설득형 등 문체를 적용\n" +
            "5. 행운의 코디: 1가지씩 선정(성별에 적합한 아이템을 선정,색상+아이템 조합,각각 10~15자)\n" +
            "6. 불운과 행운 색은 겹치지 않는다.\n" +
            "7. 오늘 운세는 '(성 빼고 이름), '으로 시작한다."
        );

        return prompt.toString();
    }
}
