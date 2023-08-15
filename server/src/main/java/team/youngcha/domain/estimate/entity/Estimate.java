package team.youngcha.domain.estimate.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Estimate {

    private Long id;
    private Long trimId;
    private Long engineId;
    private Long bodyTypeId;
    private Long drivingSystemId;
    private Long exteriorColorId;
    private Long interiorColorId;
    private Long wheelId;
    private Long keyword1Id;
    private Long keyword2Id;
    private Long keyword3Id;
    private int ageRange;
    private int gender;
    private LocalDateTime createDate;

    @Builder
    public Estimate(Long id, Long trimId, Long engineId, Long bodyTypeId, Long drivingSystemId, Long exteriorColorId, Long interiorColorId, Long wheelId, Long keyword1Id, Long keyword2Id, Long keyword3Id, int ageRange, int gender) {
        this.id = id;
        this.trimId = trimId;
        this.engineId = engineId;
        this.bodyTypeId = bodyTypeId;
        this.drivingSystemId = drivingSystemId;
        this.exteriorColorId = exteriorColorId;
        this.interiorColorId = interiorColorId;
        this.wheelId = wheelId;
        this.keyword1Id = keyword1Id;
        this.keyword2Id = keyword2Id;
        this.keyword3Id = keyword3Id;
        this.ageRange = ageRange;
        this.gender = gender;
        this.createDate = LocalDateTime.now();
    }
}
