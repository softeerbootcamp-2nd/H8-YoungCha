package team.youngcha.domain.estimate.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EstimateSelectiveOption {

    private Long id;
    private Long estimateId;
    private Long optionId;
}
