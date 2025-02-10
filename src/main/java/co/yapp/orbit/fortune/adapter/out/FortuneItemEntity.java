package co.yapp.orbit.fortune.adapter.out;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class FortuneItemEntity {

    private int score;
    private String description;

    public FortuneItemEntity(int score, String description) {
        this.score = score;
        this.description = description;
    }
}
