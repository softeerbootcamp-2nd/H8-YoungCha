package team.youngcha.domain.estimate.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Estimate {

    private Long id;
    private Long trimId;
    private Long engineId;
    private Long bodyTypeId;
    private Long drivingSystemId;
    private Long exteriorColorId;
    private Long InteriorColorId;
    private Long wheelId;
    private int ageRange;
    private int gender;
    private LocalDateTime createDate;

    public Estimate(Long trimId, Long engineId, Long bodyTypeId, Long drivingSystemId, Long exteriorColorId, Long interiorColorId, Long wheelId, int ageRange, int gender, LocalDateTime createDate) {
        this.trimId = trimId;
        this.engineId = engineId;
        this.bodyTypeId = bodyTypeId;
        this.drivingSystemId = drivingSystemId;
        this.exteriorColorId = exteriorColorId;
        InteriorColorId = interiorColorId;
        this.wheelId = wheelId;
        this.ageRange = ageRange;
        this.gender = gender;
        this.createDate = createDate;
    }
}
