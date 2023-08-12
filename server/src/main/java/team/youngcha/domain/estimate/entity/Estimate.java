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
    private Long keywordId1;
    private Long keywordId2;
    private Long keywordId3;
    private int ageRange;
    private int gender;
    private LocalDateTime createDate;

    @Builder
    public Estimate(Long id, Long trimId, Long engineId, Long bodyTypeId, Long drivingSystemId, Long exteriorColorId, Long interiorColorId, Long wheelId, Long keywordId1, Long keywordId2, Long keywordId3, int ageRange, int gender) {
        this.id = id;
        this.trimId = trimId;
        this.engineId = engineId;
        this.bodyTypeId = bodyTypeId;
        this.drivingSystemId = drivingSystemId;
        this.exteriorColorId = exteriorColorId;
        this.interiorColorId = interiorColorId;
        this.wheelId = wheelId;
        this.keywordId1 = keywordId1;
        this.keywordId2 = keywordId2;
        this.keywordId3 = keywordId3;
        this.ageRange = ageRange;
        this.gender = gender;
        this.createDate = LocalDateTime.now();
    }
}
