package co.yapp.orbit.user.adapter.in;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.yapp.orbit.user.adapter.in.request.SaveUserRequest;
import co.yapp.orbit.user.application.exception.UserNotFoundException;
import co.yapp.orbit.user.application.port.in.LoadUserUseCase;
import co.yapp.orbit.user.application.port.in.SaveUserUseCase;
import co.yapp.orbit.user.application.port.in.SaveUserCommand;
import co.yapp.orbit.user.domain.CalendarType;
import co.yapp.orbit.user.domain.Gender;
import co.yapp.orbit.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureWebMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SaveUserUseCase saveUserUseCase;

    @MockitoBean
    private LoadUserUseCase getUserUseCase;

    @Test
    @DisplayName("사용자 등록 성공 시 200 OK와 사용자 id를 반환한다.")
    void saveUser_success() throws Exception {
        // given
        Long userId = 1L;
        Mockito.when(saveUserUseCase.saveUser(any(SaveUserCommand.class)))
            .thenReturn(userId);

        SaveUserRequest request = new SaveUserRequest("홍길동", "2025-02-09", "08:30:00", "SOLAR",
            "MALE");
        String json = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(userId)));
    }

    @Test
    @DisplayName("사용자 등록 시 이름이 null이면 400 Bad Request를 반환한다.")
    void saveUser_nullName_returnsBadRequest() throws Exception {
        SaveUserRequest request = new SaveUserRequest(null, "2025-02-09", "08:30:00", "SOLAR", "MALE");
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 등록 시 생년월일이 빈 문자열이면 400 Bad Request를 반환한다.")
    void saveUser_emptyBirthDate_returnsBadRequest() throws Exception {
        SaveUserRequest request = new SaveUserRequest("홍길동", "", "08:30:00", "SOLAR", "MALE");
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 등록 시 유효하지 않은 성별이면 내부에서 예외가 발생하여 500 Internal Server Error를 반환한다.")
    void createUser_invalidGender_returnsInternalServerError() throws Exception {
        SaveUserRequest request = new SaveUserRequest("홍길동", "2025-02-09", "08:30:00", "SOLAR", "INVALID");
        String json = objectMapper.writeValueAsString(request);

        Mockito.doThrow(new IllegalArgumentException("No enum constant"))
            .when(saveUserUseCase).saveUser(any(SaveUserCommand.class));

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("사용자 조회 시 존재하지 않으면 404 Not Found를 반환한다.")
    void getUser_notFound_returnsNotFound() throws Exception {
        Long userId = 1L;
        Mockito.when(getUserUseCase.loadUser(userId))
            .thenThrow(new UserNotFoundException("사용자를 찾을 수 없습니다. id: " + userId));

        mockMvc.perform(get("/api/v1/users/{id}", userId))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("사용자 조회 시 정상적인 경우 200 OK와 사용자 정보를 반환한다.")
    void getUser_success() throws Exception {
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

        mockMvc.perform(get("/api/v1/users/{id}", userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.name").value("홍길동"))
            .andExpect(jsonPath("$.birthDate").value("2025-02-09"))
            .andExpect(jsonPath("$.birthTime").value("08:30"))
            .andExpect(jsonPath("$.gender").value("MALE"));
    }
}