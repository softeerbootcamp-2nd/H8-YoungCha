package team.youngcha.domain.option.entity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionDetail {

    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private Long optionId;

    @Builder
    public OptionDetail(Long id, String name, String description, String imgUrl, Long optionId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.optionId = optionId;
    }
}
