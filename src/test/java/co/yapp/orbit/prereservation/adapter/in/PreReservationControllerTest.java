package co.yapp.orbit.prereservation.adapter.in;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.yapp.orbit.prereservation.adapter.in.request.PreReservationCreateRequest;
import co.yapp.orbit.prereservation.application.exception.DuplicatePreReservationException;
import co.yapp.orbit.prereservation.application.exception.InvalidPreReservationCommandException;
import co.yapp.orbit.prereservation.application.port.in.CreatePreReservationUseCase;
import co.yapp.orbit.prereservation.application.port.in.PreReservationCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(controllers = PreReservationController.class)
@AutoConfigureWebMvc
class PreReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreatePreReservationUseCase createPreReservationUseCase;

    @Test
    @DisplayName("새로운 사전 예약 생성시 200 OK 응답을 반환한다.")
    void createPreReservation_shouldReturnOk() throws Exception {
        // given
        // Service 레이어가 정상 동작한다고 가정
        Mockito.doNothing().when(createPreReservationUseCase).createPreReservation(any(
            PreReservationCommand.class));

        // when & then
        mockMvc.perform(post("/api/v1/prereservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"byungwook-min@naver.com\", \"phoneNumber\": \"010-1234-5678\"}"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("이메일이 null이면 400 Bad Request")
    void createPreReservation_nullName() throws Exception {
        PreReservationCreateRequest req = new PreReservationCreateRequest(null, "010-1234-5678");

        mockMvc.perform(
                post("/api/v1/prereservations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("전화번호가 빈문자면 400 Bad Request")
    void createPreReservation_emptyPhoneNumber() throws Exception {
        PreReservationCreateRequest req = new PreReservationCreateRequest("홍길동", "");

        mockMvc.perform(
                post("/api/v1/prereservations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidPreReservationCommandException));
    }

    @Test
    @DisplayName("이미 존재할 경우 409 CONFLICT 응답을 반환한다.")
    void createPreReservation_whenDuplicate_shouldReturn409() throws Exception {
        // given
        Mockito.doThrow(new DuplicatePreReservationException("이미 동일한 이메일과 전화번호로 사전예약이 존재합니다."))
            .when(createPreReservationUseCase)
            .createPreReservation(any(PreReservationCommand.class));

        // when & then
        mockMvc.perform(post("/api/v1/prereservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"byungwook-min@naver.com\", \"phoneNumber\": \"010-1234-5678\"}"))
            .andExpect(status().isConflict());
    }
}