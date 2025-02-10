package co.yapp.orbit.fortune.domain;

import java.util.Arrays;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Fortune {

    private final Long id;

    private final String dailyFortune;

    private final int avgFortuneScore;
    private final FortuneItem studyCareerFortune;
    private final FortuneItem wealthFortune;
    private final FortuneItem healthFortune;
    private final FortuneItem loveFortune;

    private final String luckyOutfitTop;
    private final String luckyOutfitBottom;
    private final String luckyOutfitShoes;
    private final String luckyOutfitAccessory;

    private final String unluckyColor;
    private final String luckyColor;
    private final String luckyFood;

    @Builder
    public Fortune(Long id, String dailyFortune, int avgFortuneScore,
        FortuneItem studyCareerFortune,
        FortuneItem wealthFortune, FortuneItem healthFortune, FortuneItem loveFortune,
        String luckyOutfitTop, String luckyOutfitBottom, String luckyOutfitShoes,
        String luckyOutfitAccessory, String unluckyColor, String luckyColor, String luckyFood) {
        this.id = id;
        this.dailyFortune = dailyFortune;
        this.avgFortuneScore = avgFortuneScore;
        this.studyCareerFortune = studyCareerFortune;
        this.wealthFortune = wealthFortune;
        this.healthFortune = healthFortune;
        this.loveFortune = loveFortune;
        this.luckyOutfitTop = luckyOutfitTop;
        this.luckyOutfitBottom = luckyOutfitBottom;
        this.luckyOutfitShoes = luckyOutfitShoes;
        this.luckyOutfitAccessory = luckyOutfitAccessory;
        this.unluckyColor = unluckyColor;
        this.luckyColor = luckyColor;
        this.luckyFood = luckyFood;
    }

    public static Fortune create(Long id, String dailyFortune, FortuneItem studyCareerFortune,
        FortuneItem wealthFortune, FortuneItem healthFortune, FortuneItem loveFortune,
        String luckyOutfitTop, String luckyOutfitBottom, String luckyOutfitShoes,
        String luckyOutfitAccessory, String unluckyColor, String luckyColor, String luckyFood) {

        int avgFortuneScore = calcAvgFortuneScore(
            studyCareerFortune.getScore(),
            wealthFortune.getScore(),
            healthFortune.getScore(),
            loveFortune.getScore()
        );

        return Fortune.builder()
            .id(id)
            .dailyFortune(dailyFortune)
            .avgFortuneScore(avgFortuneScore)
            .studyCareerFortune(studyCareerFortune)
            .wealthFortune(wealthFortune)
            .healthFortune(healthFortune)
            .loveFortune(loveFortune)
            .luckyOutfitTop(luckyOutfitTop)
            .luckyOutfitBottom(luckyOutfitBottom)
            .luckyOutfitShoes(luckyOutfitShoes)
            .luckyOutfitAccessory(luckyOutfitAccessory)
            .unluckyColor(unluckyColor)
            .luckyColor(luckyColor)
            .luckyFood(luckyFood)
            .build();
    }

    private static int calcAvgFortuneScore(int... scores) {
        return (int) Arrays.stream(scores).average().orElse(0);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fortune fortune = (Fortune) o;
        return getAvgFortuneScore() == fortune.getAvgFortuneScore() && Objects.equals(getId(),
            fortune.getId()) && Objects.equals(getDailyFortune(), fortune.getDailyFortune())
            && Objects.equals(getStudyCareerFortune(), fortune.getStudyCareerFortune())
            && Objects.equals(getWealthFortune(), fortune.getWealthFortune())
            && Objects.equals(getHealthFortune(), fortune.getHealthFortune())
            && Objects.equals(getLoveFortune(), fortune.getLoveFortune())
            && Objects.equals(getLuckyOutfitTop(), fortune.getLuckyOutfitTop())
            && Objects.equals(getLuckyOutfitBottom(), fortune.getLuckyOutfitBottom())
            && Objects.equals(getLuckyOutfitShoes(), fortune.getLuckyOutfitShoes())
            && Objects.equals(getLuckyOutfitAccessory(), fortune.getLuckyOutfitAccessory())
            && Objects.equals(getUnluckyColor(), fortune.getUnluckyColor())
            && Objects.equals(getLuckyColor(), fortune.getLuckyColor())
            && Objects.equals(getLuckyFood(), fortune.getLuckyFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDailyFortune(), getAvgFortuneScore(),
            getStudyCareerFortune(),
            getWealthFortune(), getHealthFortune(), getLoveFortune(), getLuckyOutfitTop(),
            getLuckyOutfitBottom(), getLuckyOutfitShoes(), getLuckyOutfitAccessory(),
            getUnluckyColor(),
            getLuckyColor(), getLuckyFood());
    }
}
