package co.yapp.orbit.fortune.adapter.in.response;

import co.yapp.orbit.fortune.domain.Fortune;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoadFortuneResponse {

    private final Long id;

    private final String dailyFortune;

    private final int avgFortuneScore;
    private final FortuneItemResponse studyCareerFortune;
    private final FortuneItemResponse wealthFortune;
    private final FortuneItemResponse healthFortune;
    private final FortuneItemResponse loveFortune;

    private final String luckyOutfitTop;
    private final String luckyOutfitBottom;
    private final String luckyOutfitShoes;
    private final String luckyOutfitAccessory;

    private final String unluckyColor;
    private final String luckyColor;
    private final String luckyFood;

    @Builder
    public LoadFortuneResponse(Long id, String dailyFortune, int avgFortuneScore,
        FortuneItemResponse studyCareerFortune, FortuneItemResponse wealthFortune,
        FortuneItemResponse healthFortune, FortuneItemResponse loveFortune, String luckyOutfitTop,
        String luckyOutfitBottom, String luckyOutfitShoes, String luckyOutfitAccessory,
        String unluckyColor, String luckyColor, String luckyFood) {
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

    public static LoadFortuneResponse of(Fortune fortune) {

        return LoadFortuneResponse.builder()
            .id(fortune.getId())
            .dailyFortune(fortune.getDailyFortune())
            .avgFortuneScore(fortune.getAvgFortuneScore())
            .studyCareerFortune(
                FortuneItemResponse.builder()
                    .score(fortune.getStudyCareerFortune().getScore())
                    .description(fortune.getStudyCareerFortune().getDescription())
                    .build()
            )
            .wealthFortune(
                FortuneItemResponse.builder()
                    .score(fortune.getWealthFortune().getScore())
                    .description(fortune.getWealthFortune().getDescription())
                    .build()
            )
            .healthFortune(
                FortuneItemResponse.builder()
                    .score(fortune.getHealthFortune().getScore())
                    .description(fortune.getHealthFortune().getDescription())
                    .build()
            )
            .loveFortune(
                FortuneItemResponse.builder()
                    .score(fortune.getLoveFortune().getScore())
                    .description(fortune.getLoveFortune().getDescription())
                    .build()
            )
            .luckyOutfitTop(fortune.getLuckyOutfitTop())
            .luckyOutfitBottom(fortune.getLuckyOutfitBottom())
            .luckyOutfitShoes(fortune.getLuckyOutfitShoes())
            .luckyOutfitAccessory(fortune.getLuckyOutfitAccessory())
            .unluckyColor(fortune.getUnluckyColor())
            .luckyColor(fortune.getLuckyColor())
            .luckyFood(fortune.getLuckyFood())
            .build();
    }

    @Getter
    public static class FortuneItemResponse {
        private final int score;
        private final String description;

        @Builder
        public FortuneItemResponse(int score, String description) {
            this.score = score;
            this.description = description;
        }
    }
}
