package team.youngcha.domain.option.dto;

import lombok.Getter;
import team.youngcha.domain.option.entity.OptionDetail;

@Getter
public class FindOptionDetailResponse {

    private String name;
    private String description;
    private String imgUrl;

    public FindOptionDetailResponse(OptionDetail optionDetail) {
        this.name = optionDetail.getName();
        this.description = optionDetail.getDescription();
        this.imgUrl = optionDetail.getImgUrl();
    }
}
