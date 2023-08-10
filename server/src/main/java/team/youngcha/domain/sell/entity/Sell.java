package team.youngcha.domain.sell.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Sell {

    private Long id;
    private Long trimId;
    private Long engineId;
    private Long bodyTypeId;
    private Long drivingSystemId;
    private Long exteriorColorId;
    private Long InteriorColorId;
    private Long wheelId;
    private int age;
    private int gender;
    private LocalDateTime createDate;

    public Sell(Long trimId, Long engineId, Long bodyTypeId, Long drivingSystemId, Long exteriorColorId, Long interiorColorId, Long wheelId, int age, int gender, LocalDateTime createDate) {
        this.trimId = trimId;
        this.engineId = engineId;
        this.bodyTypeId = bodyTypeId;
        this.drivingSystemId = drivingSystemId;
        this.exteriorColorId = exteriorColorId;
        InteriorColorId = interiorColorId;
        this.wheelId = wheelId;
        this.age = age;
        this.gender = gender;
        this.createDate = createDate;
    }
}
