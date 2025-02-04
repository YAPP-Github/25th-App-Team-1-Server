package co.yapp.orbit.prereservation.adapter.out;

import co.yapp.orbit.prereservation.application.port.out.NotifyPreReservationPort;
import co.yapp.orbit.prereservation.domain.PreReservation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DiscordWebhookAdapter implements NotifyPreReservationPort {

    private final WebClient webClient;
    private final String discordWebhookUrl;

    public DiscordWebhookAdapter(
        @Value("${discord.webhook.url}") String discordWebhookUrl
    ) {
        this.webClient = WebClient.create();
        this.discordWebhookUrl = discordWebhookUrl;
    }

    @Override
    public void notify(PreReservation preReservation) {
        // 예시로 JSON 바디로 전달
        String content = String.format("새로운 사전 예약이 등록되었습니다! 이메일: %s, 전화번호: %s",
            preReservation.getEmail(), preReservation.getPhoneNumber());

        webClient.post()
            .uri(discordWebhookUrl)
            .bodyValue(new DiscordWebhookRequest(content))
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    @Setter
    @Getter
    static class DiscordWebhookRequest {
        private String content;

        public DiscordWebhookRequest(String content) {
            this.content = content;
        }

    }
}
