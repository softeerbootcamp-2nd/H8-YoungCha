package team.youngcha.domain.trim.entity;

import lombok.Getter;

@Getter
public class TrimOption {

    private Long id;
    private int type;
    private Long trimId;
    private Long optionId;

    public TrimOption(int type, Long trimId, Long optionId) {
        this.type = type;
        this.trimId = trimId;
        this.optionId = optionId;
    }
}
