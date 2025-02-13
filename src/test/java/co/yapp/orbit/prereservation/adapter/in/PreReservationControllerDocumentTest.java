package co.yapp.orbit.prereservation.adapter.in;


import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.yapp.orbit.prereservation.adapter.in.request.PreReservationCreateRequest;
import co.yapp.orbit.prereservation.application.port.in.CreatePreReservationUseCase;
import co.yapp.orbit.prereservation.application.port.in.PreReservationCommand;
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

@WebMvcTest(controllers = PreReservationController.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.orbitalarm.net", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
class PreReservationControllerDocumentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreatePreReservationUseCase createPreReservationUseCase;

    @Test
    @DisplayName("문서화: 사전 예약 생성 API")
    void createPreReservation_document() throws Exception {
        // given
        PreReservationCreateRequest request = new PreReservationCreateRequest(
            "byungwook-min@naver.com", "010-1234-5678");
        String json = objectMapper.writeValueAsString(request);

        Mockito.doNothing().when(createPreReservationUseCase)
            .createPreReservation(any(PreReservationCommand.class));

        // when & then
        mockMvc.perform(post("/api/v1/prereservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andDo(document("prereservations-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("PreReservation")
                    .summary("사전 예약 생성 API")
                    .description("사전 예약을 신청하는 API")
                    .requestFields(
                        fieldWithPath("email")
                            .description("사전 예약을 신청하는 사용자의 이메일 주소"),
                        fieldWithPath("phoneNumber")
                            .description("사전 예약을 신청하는 사용자의 전화번호")
                    )
                    .requestSchema(Schema.schema("PreReservationCreateRequest"))
                    .build()
            )));
    }
}
