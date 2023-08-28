package team.youngcha.domain.option.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionDetail {

    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private Long optionId;
    private List<Spec> specs = new ArrayList<>();

    @Builder
    public OptionDetail(Long id, String name, String description, String imgUrl, Long optionId, List<Spec> specs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.optionId = optionId;
        this.specs = specs;
    }
}
