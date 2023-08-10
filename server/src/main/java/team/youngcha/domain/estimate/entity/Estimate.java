package team.youngcha.domain.estimate.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
}
