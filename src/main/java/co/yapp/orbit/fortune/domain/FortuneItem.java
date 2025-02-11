package co.yapp.orbit.fortune.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class FortuneItem {

    private int score;
    private String title;
    private String description;

    public FortuneItem(int score, String title, String description) {
        this.score = score;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FortuneItem that = (FortuneItem) o;
        return getScore() == that.getScore() && Objects.equals(getTitle(), that.getTitle())
            && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScore(), getTitle(), getDescription());
    }
}
