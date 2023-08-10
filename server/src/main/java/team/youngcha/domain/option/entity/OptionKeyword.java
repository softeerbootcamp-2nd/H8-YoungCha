package team.youngcha.domain.option.entity;

import lombok.Getter;

@Getter
public class OptionKeyword {

    private Long id;
    private Long optionId;
    private Long keywordId;

    public OptionKeyword(Long optionId, Long keywordId) {
        this.optionId = optionId;
        this.keywordId = keywordId;
    }
}
