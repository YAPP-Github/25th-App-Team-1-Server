package co.yapp.orbit.fortune.adapter.out;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class FortuneItemEntity {

    private int score;
    private String title;
    private String description;

    public FortuneItemEntity(int score, String title, String description) {
        this.score = score;
        this.title = title;
        this.description = description;
    }
}
