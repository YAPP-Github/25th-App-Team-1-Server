package co.yapp.orbit.fortune.adapter.in;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;

import co.yapp.orbit.fortune.adapter.in.response.FortuneResponse;
import co.yapp.orbit.fortune.adapter.out.exception.FortuneParsingException;
import co.yapp.orbit.fortune.adapter.out.exception.WebClientFetchException;
import co.yapp.orbit.fortune.application.exception.FortuneCreateInvalidUserException;
import co.yapp.orbit.fortune.application.exception.FortuneNotFoundException;
import co.yapp.orbit.fortune.application.exception.InvalidFortuneCommandException;
import co.yapp.orbit.fortune.application.port.in.CreateFortuneUseCase;
import co.yapp.orbit.fortune.application.port.in.LoadFortuneUseCase;
import co.yapp.orbit.fortune.domain.Fortune;
import co.yapp.orbit.fortune.domain.FortuneItem;
import co.yapp.orbit.user.application.exception.UserNotFoundException;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FortuneController.class)
@AutoConfigureWebMvc
@AutoConfigureRestDocs()
class FortuneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateFortuneUseCase createFortuneUseCase;

    @MockitoBean
    private LoadFortuneUseCase loadFortuneUseCase;

    @Test
    @DisplayName("운세 생성에 성공 시, 200 OK 응답 반환")
    void createFortune_ok() throws Exception {
        // given
        Fortune fortune = Fortune.create(
            1L,
            "Today is a great day!",
            new FortuneItem(90, "title1", "Success in studies"),
            new FortuneItem(90, "title2", "Prosperity in finance"),
            new FortuneItem(90, "title3", "Strong physical health"),
            new FortuneItem(90, "title4", "Harmonious love life"),
            "Red Shirt",
            "Black Pants",
            "White Sneakers",
            "Gold Necklace",
            "Green",
            "Blue",
            "Pizza"
        );

        FortuneResponse response = FortuneResponse.from(fortune);
        when(createFortuneUseCase.createFortune(any())).thenReturn(fortune);

        // when & then & docs
        mockMvc.perform(post("/api/v1/fortunes")
                .queryParam("userId", "1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)))
            .andDo(document("운세 생성 200 OK",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Fortune")
                    .summary("운세 생성 API")
                    .description("운세 생성 성공 시, 200 OK")
                    .queryParameters(
                        parameterWithName("userId").description("사용자 id")
                    )
                    .responseFields(
                        fieldWithPath("id").type(NUMBER).description("운세 ID"),
                        fieldWithPath("dailyFortune").type(STRING).description("오늘의 운세 메시지"),
                        fieldWithPath("avgFortuneScore").type(NUMBER).description("운세 평균 점수"),
                        fieldWithPath("studyCareerFortune.score").type(NUMBER).description("학업운 점수"),
                        fieldWithPath("studyCareerFortune.title").type(STRING).description("학업운 제목"),
                        fieldWithPath("studyCareerFortune.description").type(STRING).description("학업운 설명"),
                        fieldWithPath("wealthFortune.score").type(NUMBER).description("재물운 점수"),
                        fieldWithPath("wealthFortune.title").type(STRING).description("재물운 제목"),
                        fieldWithPath("wealthFortune.description").type(STRING).description("재물운 설명"),
                        fieldWithPath("healthFortune.score").type(NUMBER).description("건강운 점수"),
                        fieldWithPath("healthFortune.title").type(STRING).description("건강운 제목"),
                        fieldWithPath("healthFortune.description").type(STRING).description("건강운 설명"),
                        fieldWithPath("loveFortune.score").type(NUMBER).description("사랑운 점수"),
                        fieldWithPath("loveFortune.title").type(STRING).description("사랑운 제목"),
                        fieldWithPath("loveFortune.description").type(STRING).description("사랑운 설명"),
                        fieldWithPath("luckyOutfitTop").type(STRING).description("행운의 의상 상의"),
                        fieldWithPath("luckyOutfitBottom").type(STRING).description("행운의 의상 하의"),
                        fieldWithPath("luckyOutfitShoes").type(STRING).description("행운의 의상 신발"),
                        fieldWithPath("luckyOutfitAccessory").type(STRING).description("행운의 의상 액세서리"),
                        fieldWithPath("unluckyColor").type(STRING).description("불운의 색깔"),
                        fieldWithPath("luckyColor").type(STRING).description("행운의 색깔"),
                        fieldWithPath("luckyFood").type(STRING).description("행운의 음식")
                    )
                    .requestSchema(Schema.schema("POST Fortune Parameters"))
                    .responseSchema(Schema.schema("Fortune"))
                    .build()
                ))
            );
    }

    @Test
    @DisplayName("userId가 null이면 400 BAD REQUEST 응답 반환")
    void createFortune_emptyUserId() throws Exception {
        mockMvc.perform(post("/api/v1/fortunes")
                .queryParam("userId", ""))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("user id는 null 또는 빈 문자열일 수 없습니다."))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidFortuneCommandException))
            .andDo(
                document("userId가 유효하지 않을 경우, 400 BAD REQUEST",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("Fortune")
                        .summary("운세 생성 API")
                        .queryParameters(
                            parameterWithName("userId").description("사용자 ID")
                        )
                        .requestSchema(Schema.schema("POST Fortune Parameters"))
                        .responseSchema(Schema.schema("Exception"))
                        .build()
                    ))
            );
    }

    @Test
    @DisplayName("userId가 숫자가 아니면 400 BAD REQUEST 응답 반환")
    void createFortune_invalidUserId() throws Exception {
        mockMvc.perform(post("/api/v1/fortunes")
                .queryParam("userId", "abc"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("user id는 숫자여야 합니다."))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidFortuneCommandException))
            .andDo(
                document("userId가 숫자가 아닐 경우, 400 BAD REQUEST",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("Fortune")
                        .summary("운세 생성 API")
                        .queryParameters(
                            parameterWithName("userId").description("사용자 ID")
                        )
                        .requestSchema(Schema.schema("POST Fortune Parameters"))
                        .responseSchema(Schema.schema("Exception"))
                        .build()
                    ))
                );
    }

    @Test
    @DisplayName("존재하지 않는 사용자라면 400 BAD REQUEST 응답 반환")
    void createFortune_NotFoundUser() throws Exception {
        when(createFortuneUseCase.createFortune(any())).thenThrow(new FortuneCreateInvalidUserException("사용자를 찾을 수 없습니다."));

        mockMvc.perform(post("/api/v1/fortunes")
                .queryParam("userId", "999"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("사용자를 찾을 수 없습니다."))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof FortuneCreateInvalidUserException))
            .andDo(
                document("존재하지 않는 사용자일 경우, 404 NOT FOUND",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("Fortune")
                        .summary("운세 생성 API")
                        .queryParameters(
                            parameterWithName("userId").description("사용자 ID")
                        )
                        .requestSchema(Schema.schema("POST Fortune Parameters"))
                        .responseSchema(Schema.schema("Exception"))
                        .build()
                    ))
                );
    }

    @Test
    @DisplayName("운세 파싱 예외 발생 시 500 INTERNAL SERVER ERROR 반환")
    void createFortune_fortuneParsingException_shouldReturnInternalServerError() throws Exception {
        // given
        when(createFortuneUseCase.createFortune(any())).thenThrow(new FortuneParsingException("운세 데이터를 처리하는 과정에서 오류가 발생했습니다."));

        // when & then
        mockMvc.perform(post("/api/v1/fortunes")
                .queryParam("userId", "1"))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string("운세 데이터를 처리하는 과정에서 오류가 발생했습니다."))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof FortuneParsingException))
            .andDo(
                document("운세 파싱 중 오류 발생 시, 500 INTERNAL SERVER ERROR",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("Fortune")
                        .summary("운세 생성 API")
                        .queryParameters(
                            parameterWithName("userId").description("사용자 ID")
                        )
                        .requestSchema(Schema.schema("POST Fortune Parameters"))
                        .responseSchema(Schema.schema("Exception"))
                        .build()
                    ))
                );
    }

    @Test
    @DisplayName("운세 생성 시 외부 API로부터 응답이 오지 않으면 502 BAD GATEWAY 반환")
    void createFortune_fortuneFetchException_shouldReturnBadGateway() throws Exception {
        // given
        when(createFortuneUseCase.createFortune(any())).thenThrow(new WebClientFetchException("운세 데이터 요청 중 오류가 발생했습니다."));

        // when & then
        mockMvc.perform(post("/api/v1/fortunes")
                .queryParam("userId", "1"))
            .andExpect(status().isBadGateway())
            .andExpect(content().string("운세 데이터 요청 중 오류가 발생했습니다."))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof WebClientFetchException))
            .andDo(
                document("외부 API 요청 중 오류 발생 시, 502 BAD GATEWAY",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("Fortune")
                        .summary("운세 생성 API")
                        .queryParameters(
                            parameterWithName("userId").description("사용자 ID")
                        )
                        .requestSchema(Schema.schema("POST Fortune Parameters"))
                        .responseSchema(Schema.schema("Exception"))
                        .build()
                    ))
                );
    }

    @Test
    @DisplayName("운세 조회 성공 시, 200 OK 응답 반환")
    void loadFortune_ok() throws Exception {
        // given
        Long fortuneId = 1L;
        Fortune fortune = Fortune.create(
            fortuneId,
            "Today is a great day!",
            new FortuneItem(90, "title1", "Success in studies"),
            new FortuneItem(90, "title2", "Prosperity in finance"),
            new FortuneItem(90, "title3", "Strong physical health"),
            new FortuneItem(90, "title4", "Harmonious love life"),
            "Red Shirt",
            "Black Pants",
            "White Sneakers",
            "Gold Necklace",
            "Green",
            "Blue",
            "Pizza"
        );

        FortuneResponse response = FortuneResponse.from(fortune);
        when(loadFortuneUseCase.loadFortune(fortuneId)).thenReturn(fortune);

        // when & then
        mockMvc.perform(get("/api/v1/fortunes/{fortuneId}", fortuneId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)))
            .andDo(document("운세 조회 200 OK",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Fortune")
                    .summary("운세 조회 API")
                    .description("운세 조회 성공 시, 200 OK")
                    .pathParameters(
                        parameterWithName("fortuneId").description("오늘 생성된 운세 ID")
                    )
                    .responseFields(
                        fieldWithPath("id").type(NUMBER).description("운세 ID"),
                        fieldWithPath("dailyFortune").type(STRING).description("오늘의 운세 메시지"),
                        fieldWithPath("avgFortuneScore").type(NUMBER).description("운세 평균 점수"),
                        fieldWithPath("studyCareerFortune.score").type(NUMBER).description("학업운 점수"),
                        fieldWithPath("studyCareerFortune.title").type(STRING).description("학업운 제목"),
                        fieldWithPath("studyCareerFortune.description").type(STRING).description("학업운 설명"),
                        fieldWithPath("wealthFortune.score").type(NUMBER).description("재물운 점수"),
                        fieldWithPath("wealthFortune.title").type(STRING).description("재물운 제목"),
                        fieldWithPath("wealthFortune.description").type(STRING).description("재물운 설명"),
                        fieldWithPath("healthFortune.score").type(NUMBER).description("건강운 점수"),
                        fieldWithPath("healthFortune.title").type(STRING).description("건강운 제목"),
                        fieldWithPath("healthFortune.description").type(STRING).description("건강운 설명"),
                        fieldWithPath("loveFortune.score").type(NUMBER).description("사랑운 점수"),
                        fieldWithPath("loveFortune.title").type(STRING).description("사랑운 제목"),
                        fieldWithPath("loveFortune.description").type(STRING).description("사랑운 설명"),
                        fieldWithPath("luckyOutfitTop").type(STRING).description("행운의 의상 상의"),
                        fieldWithPath("luckyOutfitBottom").type(STRING).description("행운의 의상 하의"),
                        fieldWithPath("luckyOutfitShoes").type(STRING).description("행운의 의상 신발"),
                        fieldWithPath("luckyOutfitAccessory").type(STRING).description("행운의 의상 액세서리"),
                        fieldWithPath("unluckyColor").type(STRING).description("불운의 색깔"),
                        fieldWithPath("luckyColor").type(STRING).description("행운의 색깔"),
                        fieldWithPath("luckyFood").type(STRING).description("행운의 음식")
                    )
                    .requestSchema(Schema.schema("GET Fortune Parameters"))
                    .responseSchema(Schema.schema("Fortune"))
                    .build()
                ))
            );
    }

    @Test
    @DisplayName("해당 운세가 존재하지 않으면 404 NOT FOUND 응답 반환")
    void loadFortune_notFoundException_shouldReturnNotFound() throws Exception {
        // given
        Long fortuneId = 1L;
        when(loadFortuneUseCase.loadFortune(fortuneId))
            .thenThrow(new FortuneNotFoundException("해당 운세를 찾을 수 없습니다. 운세 생성을 요청해주세요."));

        // when & then
        mockMvc.perform(get("/api/v1/fortunes/{fortuneId}", fortuneId))
            .andExpect(status().isNotFound())
            .andExpect(content().string("해당 운세를 찾을 수 없습니다. 운세 생성을 요청해주세요."))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof FortuneNotFoundException))
            .andDo(
                document("존재하지 않는 운세인 경우, 404 NOT FOUND",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(ResourceSnippetParameters.builder()
                        .tag("Fortune")
                        .summary("운세 조회 API")
                        .pathParameters(
                            parameterWithName("fortuneId").description("오늘 생성된 운세 ID")
                        )
                        .requestSchema(Schema.schema("GET Fortune Parameters"))
                        .responseSchema(Schema.schema("Exception"))
                        .build()
                    ))
                );

    }
}