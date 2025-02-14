package co.yapp.orbit.user.adapter.in;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import co.yapp.orbit.user.adapter.in.request.SaveUserRequest;
import co.yapp.orbit.user.adapter.in.request.UpdateUserRequest;
import co.yapp.orbit.user.application.port.in.LoadUserUseCase;
import co.yapp.orbit.user.application.port.in.SaveUserUseCase;
import co.yapp.orbit.user.application.port.in.SaveUserCommand;
import co.yapp.orbit.user.application.port.in.UpdateUserCommand;
import co.yapp.orbit.user.application.port.in.UpdateUserUseCase;
import co.yapp.orbit.user.domain.CalendarType;
import co.yapp.orbit.user.domain.Gender;
import co.yapp.orbit.user.domain.User;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.orbitalarm.net", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
class UserControllerDocumentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SaveUserUseCase saveUserUseCase;

    @MockitoBean
    private LoadUserUseCase getUserUseCase;

    @MockitoBean
    private UpdateUserUseCase updateUserUseCase;

    @Test
    @DisplayName("문서화: 사용자 등록 API")
    void createUser_document() throws Exception {
        // given
        SaveUserRequest request = new SaveUserRequest(
            "홍길동", "2025-02-09", "08:30:00", "SOLAR", "MALE");
        String json = objectMapper.writeValueAsString(request);

        Mockito.when(saveUserUseCase.saveUser(any(SaveUserCommand.class)))
            .thenReturn(1L);

        // when & then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andDo(document("users-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("User")
                    .summary("사용자 등록 API")
                    .description("사용자를 등록하는 API")
                    .requestFields(
                        fieldWithPath("name")
                            .description("사용자의 이름"),
                        fieldWithPath("birthDate")
                            .description("사용자의 생년월일 (yyyy-MM-dd)"),
                        fieldWithPath("birthTime")
                            .description("사용자의 출생시간 (HH:mm)"),
                        fieldWithPath("calendarType")
                            .description("달력 타입 (SOLAR, LUNAR 등)"),
                        fieldWithPath("gender")
                            .description("사용자의 성별 (MALE, FEMALE 등)")
                    )
                    .requestSchema(Schema.schema("SaveUserRequest"))
                    // 응답이 사용자 ID(Long)인 경우 해당 스키마를 정의합니다.
                    .responseSchema(Schema.schema("UserId"))
                    .build()
            )));
    }

    @Test
    @DisplayName("문서화: 사용자 조회 API")
    void getUser_document() throws Exception {
        // given
        Long userId = 1L;
        User user = new User(
            userId,
            "홍길동",
            java.time.LocalDate.parse("2025-02-09"),
            java.time.LocalTime.parse("08:30:00"),
            CalendarType.SOLAR,
            Gender.MALE
        );
        Mockito.when(getUserUseCase.loadUser(userId)).thenReturn(user);

        // when & then
        mockMvc.perform(get("/api/v1/users/{id}", userId))
            .andExpect(status().isOk())
            .andDo(document("users-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("User")
                    .summary("사용자 조회 API")
                    .description("사용자 정보를 조회하는 API")
                    .pathParameters(
                        parameterWithName("id").description("사용자 ID")
                    )
                    .responseFields(
                        fieldWithPath("id")
                            .description("사용자 ID"),
                        fieldWithPath("name")
                            .description("사용자의 이름"),
                        fieldWithPath("birthDate")
                            .description("사용자의 생년월일 (yyyy-MM-dd)"),
                        fieldWithPath("birthTime")
                            .description("사용자의 출생시간 (HH:mm)"),
                        fieldWithPath("calendarType")
                            .description("달력 타입 (SOLAR, LUNAR 등)"),
                        fieldWithPath("gender")
                            .description("사용자의 성")
                    )
                    .requestSchema(Schema.schema("GET User Parameters"))
                    .responseSchema(Schema.schema("User"))
                    .build()
            )));
    }

    @Test
    @DisplayName("문서화: 사용자 수정 API")
    void updateUser_document() throws Exception {
        // given
        Long userId = 1L;
        UpdateUserRequest request = new UpdateUserRequest(
            "홍길동", "2025-02-09", "08:30:00", "SOLAR", "MALE"
        );
        String json = objectMapper.writeValueAsString(request);

        Mockito.doNothing().when(updateUserUseCase).updateUser(eq(userId), any(UpdateUserCommand.class));

        // when & then
        mockMvc.perform(put("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNoContent())
            .andDo(document("users-update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("User")
                    .summary("사용자 수정 API")
                    .description("사용자 정보를 수정하는 API (전체 덮어쓰기 방식)")
                    .pathParameters(
                        parameterWithName("id").description("수정할 사용자 ID")
                    )
                    .requestFields(
                        fieldWithPath("name").description("사용자의 이름"),
                        fieldWithPath("birthDate").description("사용자의 생년월일 (yyyy-MM-dd)"),
                        fieldWithPath("birthTime").description("사용자의 출생시간 (HH:mm)"),
                        fieldWithPath("calendarType").description("달력 타입 (SOLAR, LUNAR 등)"),
                        fieldWithPath("gender").description("사용자의 성별 (MALE, FEMALE 등)")
                    )
                    .requestSchema(Schema.schema("UpdateUserRequest"))
                    .build()
                )));
    }
}
