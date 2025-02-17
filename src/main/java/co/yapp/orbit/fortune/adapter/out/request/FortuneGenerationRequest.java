package co.yapp.orbit.fortune.adapter.out.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FortuneGenerationRequest {

    private List<Content> contents;
    private GenerationConfig generationConfig;

    @Getter @Setter
    public static class Content {
        private Parts parts;
    }

    @Getter @Setter
    public static class Parts {
        private String text;
    }

    @Getter @Setter
    public static class GenerationConfig {
        private double temperature = 1.3;
        private double topP = 0.9;
        private long seed;
    }

    public FortuneGenerationRequest(String prompt, long seed) {
        this.contents = new ArrayList<>();
        Content content = new Content();

        Parts parts = new Parts();
        parts.setText(prompt);
        content.setParts(parts);
        this.contents.add(content);

        this.generationConfig = new GenerationConfig();
        this.generationConfig.setSeed(seed);
    }
}
