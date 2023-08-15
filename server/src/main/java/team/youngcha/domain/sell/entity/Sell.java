package team.youngcha.domain.sell.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sell {

    private Long id;
    private Long trimId;
    private Long engineId;
    private Long bodyTypeId;
    private Long drivingSystemId;
    private Long exteriorColorId;
    private Long interiorColorId;
    private Long wheelId;
    private int age;
    private int gender;
    private LocalDateTime createDate;

    @Builder
    public Sell(Long id, Long trimId, Long engineId, Long bodyTypeId,
                Long drivingSystemId, Long exteriorColorId, Long interiorColorId,
                Long wheelId, int age, int gender) {
        this.id = id;
        this.trimId = trimId;
        this.engineId = engineId;
        this.bodyTypeId = bodyTypeId;
        this.drivingSystemId = drivingSystemId;
        this.exteriorColorId = exteriorColorId;
        this.interiorColorId = interiorColorId;
        this.wheelId = wheelId;
        this.age = age;
        this.gender = gender;
        this.createDate = LocalDateTime.now();
    }
}
