package team.youngcha.domain.option.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spec {

    private Long id;
    private String name;
    private String description;
    private Long optionDetailId;

    @Builder
    public Spec(Long id, String name, String description, Long optionDetailId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.optionDetailId = optionDetailId;
    }
}
