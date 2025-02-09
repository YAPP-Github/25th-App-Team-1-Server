package co.yapp.orbit.fortune.adapter.out.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FortuneAiRequest {

    private List<Content> contents;

    @Getter @Setter
    public static class Content {
        private Parts parts;
    }

    @Getter @Setter
    public static class Parts {
        private String text;
    }

    public FortuneAiRequest(String prompt) {
        this.contents = new ArrayList<>();
        Content content = new Content();

        Parts parts = new Parts();
        parts.setText(prompt);
        content.setParts(parts);

        this.contents.add(content);
    }
}
