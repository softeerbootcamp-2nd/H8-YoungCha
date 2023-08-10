package team.youngcha.domain.sell.entity;

import lombok.Getter;

@Getter
public class SellSelectiveOption {

    private Long id;
    private Long sellId;
    private Long optionId;

    public SellSelectiveOption(Long sellId, Long optionId) {
        this.sellId = sellId;
        this.optionId = optionId;
    }
}
