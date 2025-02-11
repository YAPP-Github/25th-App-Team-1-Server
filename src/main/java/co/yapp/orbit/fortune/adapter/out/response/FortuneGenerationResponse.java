package co.yapp.orbit.fortune.adapter.out.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FortuneGenerationResponse {

    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;

    @Getter @Setter
    public static class Candidate {
        private Content content;
        private String finishReason;
        private double avgLogprobs;
    }

    @Getter @Setter
    @ToString
    public static class Content {
        private List<Part> parts;
        private String role;
    }

    @Getter @Setter
    @ToString
    public static class Part {
        private String text;
    }

    @Getter @Setter
    public static class UsageMetadata {
        private int promptTokenCount;
        private int candidatesTokenCount;
        private int totalTokenCount;
        private List<TokensDetail> promptTokensDetails;
        private List<TokensDetail> candidatesTokensDetails;
    }

    @Getter @Setter
    public static class TokensDetail {
        private String modality;
        private int tokenCount;
    }
}
