package co.yapp.orbit.prereservation.adapter.in;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.yapp.orbit.prereservation.application.port.in.CreatePreReservationUseCase;
import co.yapp.orbit.prereservation.application.port.in.PreReservationCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;


@WebMvcTest(controllers = PreReservationController.class)
class PreReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
                .content("{\"name\": \"홍길동\", \"phoneNumber\": \"010-1234-5678\"}"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("이미 존재할 경우 409 CONFLICT 응답을 반환한다.")
    void createPreReservation_whenDuplicate_shouldReturn409() throws Exception {
        // given
        Mockito.doThrow(new IllegalArgumentException("이미 동일한 이름과 전화번호로 사전예약이 존재합니다."))
            .when(createPreReservationUseCase)
            .createPreReservation(any(PreReservationCommand.class));

        // when & then
        mockMvc.perform(post("/api/v1/prereservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"홍길동\", \"phoneNumber\": \"010-1234-5678\"}"))
            .andExpect(status().isConflict())
            .andExpect(content().string("이미 동일한 이름과 전화번호로 사전예약이 존재합니다."));
    }
}