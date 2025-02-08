package co.yapp.orbit.fortune.adapter.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import co.yapp.orbit.fortune.adapter.out.exception.FortuneFetchException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "gemini.api.url=http://gemini-dummy")
class GeminiApiAdapterTest {

    private MockWebServer mockWebServer;

    @Autowired
    private GeminiApiAdapter geminiApiAdapter;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url("/").toString();
        geminiApiAdapter = new GeminiApiAdapter(baseUrl);
    }

    @AfterEach
    void terminate() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("Gemini API가 정상적으로 응답하면, 운세를 성공적으로 반환한다.")
    void loadFortune_thenSuccess() throws Exception {
        // given
        String validApiResponse = "{ \"candidates\": [ { \"content\": { \"parts\": [ { \"text\": \"운세 내용\" } ] } } ] }";

        mockWebServer.enqueue(new MockResponse()
            .setBody(validApiResponse)
            .addHeader("Content-Type", "application/json"));

        // when
        String response = geminiApiAdapter.loadFortune();

        // then
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("운세 내용");
    }

    @Test
    @DisplayName("외부 API가 빈 응답을 반환하면 FortuneFetchException을 발생시킨다.")
    void loadFortune_whenEmptyResponse_thenThrowException() {
        // given
        String emptyApiResponse = "{ \"candidates\": [ { \"content\": { \"parts\": [ { \"text\": \"\" } ] } } ] }";
        mockWebServer.enqueue(new MockResponse()
            .setBody(emptyApiResponse)
            .addHeader("Content-Type", "application/json"));

        // when & then
        assertThrows(FortuneFetchException.class, () -> geminiApiAdapter.loadFortune());
    }

    @Test
    @DisplayName("외부 API 호출 중 예외가 발생하면 FortuneFetchException을 발생시킨다.")
    void loadFortune_whenApiCallThrowsException_thenThrowFortuneFetchException() {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        // when & then
        assertThrows(FortuneFetchException.class, () -> geminiApiAdapter.loadFortune());
    }
}