package team.youngcha.domain.option.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionImage {

    private Long id;
    private String imgUrl;
    private int imgType;
    private Long optionId;

    @Builder
    public OptionImage(Long id, String imgUrl, int imgType, Long optionId) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.imgType = imgType;
        this.optionId = optionId;
    }
}
