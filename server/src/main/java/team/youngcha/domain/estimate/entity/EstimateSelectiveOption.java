package team.youngcha.domain.estimate.entity;

import lombok.Getter;

@Getter
public class EstimateSelectiveOption {

    private Long id;
    private Long estimateId;
    private Long optionId;

    public EstimateSelectiveOption(Long estimateId, Long optionId) {
        this.estimateId = estimateId;
        this.optionId = optionId;
    }
}
