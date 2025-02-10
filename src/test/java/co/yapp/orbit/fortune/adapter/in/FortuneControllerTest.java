package co.yapp.orbit.fortune.adapter.in;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.yapp.orbit.fortune.adapter.in.response.LoadFortuneResponse;
import co.yapp.orbit.fortune.adapter.out.exception.FortuneFetchException;
import co.yapp.orbit.fortune.application.exception.FortuneNotFoundException;
import co.yapp.orbit.fortune.application.exception.FortuneParsingException;
import co.yapp.orbit.fortune.application.exception.InvalidFortuneCommandException;
import co.yapp.orbit.fortune.application.port.in.CreateFortuneUseCase;
import co.yapp.orbit.fortune.application.port.in.LoadFortuneUseCase;
import co.yapp.orbit.fortune.domain.Fortune;
import co.yapp.orbit.fortune.domain.FortuneItem;
import co.yapp.orbit.prereservation.adapter.in.PreReservationController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FortuneController.class)
@AutoConfigureWebMvc
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
            new FortuneItem(90, "Success in studies"),
            new FortuneItem(90, "Prosperity in finance"),
            new FortuneItem(90, "Strong physical health"),
            new FortuneItem(90, "Harmonious love life"),
            "Red Shirt",
            "Black Pants",
            "White Sneakers",
            "Gold Necklace",
            "Green",
            "Blue",
            "Pizza"
        );

        LoadFortuneResponse response = LoadFortuneResponse.from(fortune);
        when(createFortuneUseCase.createFortune(any())).thenReturn(fortune);

        // when & then
        mockMvc.perform(post("/api/v1/fortunes")
                .param("userId", "1"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("userId가 null이면 400 BAD REQUEST 응답 반환")
    void createFortune_emptyUserId() throws Exception {
        mockMvc.perform(post("/api/v1/fortunes")
                .param("userId", ""))
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidFortuneCommandException));
    }

    @Test
    @DisplayName("userId가 숫자가 아니면 400 BAD REQUEST 응답 반환")
    void createFortune_invalidUserId() throws Exception {
        mockMvc.perform(post("/api/v1/fortunes")
                .param("userId", "abc"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidFortuneCommandException));
    }

    @Test
    @DisplayName("사주 파싱 예외 발생 시 500 INTERNAL SERVER ERROR 반환")
    void createFortune_fortuneParsingException_shouldReturnInternalServerError() throws Exception {
        // given
        when(createFortuneUseCase.createFortune(any())).thenThrow(new FortuneParsingException("Fortune parsing error"));

        // when & then
        mockMvc.perform(post("/api/v1/fortunes")
                .param("userId", "1"))
            .andExpect(status().isInternalServerError())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof FortuneParsingException));
    }

    @Test
    @DisplayName("사주 생성 시 외부 API로부터 응답이 오지 않으면 502 BAD GATEWAY 반환")
    void createFortune_fortuneFetchException_shouldReturnBadGateway() throws Exception {
        // given
        when(createFortuneUseCase.createFortune(any())).thenThrow(new FortuneFetchException("Failed to fetch fortune"));

        // when & then
        mockMvc.perform(post("/api/v1/fortunes")
                .param("userId", "1"))
            .andExpect(status().isBadGateway())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof FortuneFetchException));
    }

    @Test
    @DisplayName("운세 조회 성공 시, 200 OK 응답 반환")
    void loadFortune_ok() throws Exception {
        // given
        Long fortuneId = 1L;
        Fortune fortune = Fortune.create(
            fortuneId,
            "Today is a great day!",
            new FortuneItem(90, "Success in studies"),
            new FortuneItem(90, "Prosperity in finance"),
            new FortuneItem(90, "Strong physical health"),
            new FortuneItem(90, "Harmonious love life"),
            "Red Shirt",
            "Black Pants",
            "White Sneakers",
            "Gold Necklace",
            "Green",
            "Blue",
            "Pizza"
        );

        LoadFortuneResponse response = LoadFortuneResponse.from(fortune);
        when(loadFortuneUseCase.loadFortune(fortuneId)).thenReturn(fortune);

        // when & then
        mockMvc.perform(get("/api/v1/fortunes/{fortuneId}", fortuneId))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("해당 운세가 존재하지 않으면 404 NOT FOUND 응답 반환")
    void loadFortune_notFoundException_shouldReturnNotFound() throws Exception {
        // given
        Long fortuneId = 1L;
        when(loadFortuneUseCase.loadFortune(fortuneId))
            .thenThrow(new FortuneNotFoundException("해당 운세를 찾을 수 없습니다."));

        // when & then
        mockMvc.perform(get("/api/v1/fortunes/{fortuneId}", fortuneId))
            .andExpect(status().isNotFound());
    }
}