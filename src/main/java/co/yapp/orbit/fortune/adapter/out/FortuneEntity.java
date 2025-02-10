package co.yapp.orbit.fortune.adapter.out;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FortuneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dailyFortune;
    private int avgFortuneScore;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "score", column = @Column(name = "STUDY_CAREER_SCORE")),
        @AttributeOverride(name = "description", column = @Column(name = "STUDY_CAREER_DESCRIPTION"))
    })
    private FortuneItemEntity studyCareerFortune;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "score", column = @Column(name = "WEALTH_SCORE")),
        @AttributeOverride(name = "description", column = @Column(name = "WEALTH_DESCRIPTION"))
    })
    private FortuneItemEntity wealthFortune;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "score", column = @Column(name = "HEALTH_SCORE")),
        @AttributeOverride(name = "description", column = @Column(name = "HEALTH_DESCRIPTION"))
    })
    private FortuneItemEntity healthFortune;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "score", column = @Column(name = "LOVE_SCORE")),
        @AttributeOverride(name = "description", column = @Column(name = "LOVE_DESCRIPTION"))
    })
    private FortuneItemEntity loveFortune;

    private String luckyOutfitTop;
    private String luckyOutfitBottom;
    private String luckyOutfitShoes;
    private String luckyOutfitAccessory;

    private String unluckyColor;
    private String luckyColor;
    private String luckyFood;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public FortuneEntity(String dailyFortune, int avgFortuneScore,
        FortuneItemEntity studyCareerFortune,
        FortuneItemEntity wealthFortune, FortuneItemEntity healthFortune,
        FortuneItemEntity loveFortune,
        String luckyOutfitTop, String luckyOutfitBottom, String luckyOutfitShoes,
        String luckyOutfitAccessory, String unluckyColor, String luckyColor, String luckyFood) {
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
}
