package co.yapp.orbit.fortune.adapter.out;

import co.yapp.orbit.fortune.application.port.out.LoadFortunePort;
import co.yapp.orbit.fortune.application.port.out.SaveFortunePort;
import co.yapp.orbit.fortune.domain.Fortune;
import co.yapp.orbit.fortune.domain.FortuneItem;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class FortunePersistenceAdapter implements SaveFortunePort, LoadFortunePort {

    private final FortuneRepository fortuneRepository;

    public FortunePersistenceAdapter(FortuneRepository fortuneRepository) {
        this.fortuneRepository = fortuneRepository;
    }

    @Override
    public Long save(Fortune fortune) {
        FortuneItemEntity studyCareerFortune = new FortuneItemEntity(fortune.getStudyCareerFortune().getScore(), fortune.getStudyCareerFortune().getDescription());
        FortuneItemEntity wealthFortune = new FortuneItemEntity(fortune.getWealthFortune().getScore(), fortune.getWealthFortune().getDescription());
        FortuneItemEntity healthFortune = new FortuneItemEntity(fortune.getHealthFortune().getScore(), fortune.getHealthFortune().getDescription());
        FortuneItemEntity loveFortune = new FortuneItemEntity(fortune.getLoveFortune().getScore(), fortune.getLoveFortune().getDescription());

        FortuneEntity fortuneEntity = FortuneEntity.builder()
            .dailyFortune(fortune.getDailyFortune())
            .avgFortuneScore(fortune.getAvgFortuneScore())
            .studyCareerFortune(studyCareerFortune)
            .wealthFortune(wealthFortune)
            .healthFortune(healthFortune)
            .loveFortune(loveFortune)
            .luckyOutfitTop(fortune.getLuckyOutfitTop())
            .luckyOutfitBottom(fortune.getLuckyOutfitBottom())
            .luckyOutfitShoes(fortune.getLuckyOutfitShoes())
            .luckyOutfitAccessory(fortune.getLuckyOutfitAccessory())
            .unluckyColor(fortune.getUnluckyColor())
            .luckyColor(fortune.getLuckyColor())
            .luckyFood(fortune.getLuckyFood())
            .build();

        fortuneRepository.save(fortuneEntity);
        return fortuneEntity.getId();
    }

    @Override
    public Optional<Fortune> findById(Long id) {
        return fortuneRepository.findById(id)
            .map(entity -> Fortune.from(
                entity.getId(),
                entity.getDailyFortune(),
                new FortuneItem(entity.getStudyCareerFortune().getScore(), entity.getStudyCareerFortune().getDescription()),
                new FortuneItem(entity.getWealthFortune().getScore(), entity.getWealthFortune().getDescription()),
                new FortuneItem(entity.getHealthFortune().getScore(), entity.getHealthFortune().getDescription()),
                new FortuneItem(entity.getLoveFortune().getScore(), entity.getLoveFortune().getDescription()),
                entity.getLuckyOutfitTop(),
                entity.getLuckyOutfitBottom(),
                entity.getLuckyOutfitShoes(),
                entity.getLuckyOutfitAccessory(),
                entity.getUnluckyColor(),
                entity.getLuckyColor(),
                entity.getLuckyFood()
            ));
    }
}
