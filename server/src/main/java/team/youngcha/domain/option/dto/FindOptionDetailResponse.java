package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import team.youngcha.domain.option.entity.OptionDetail;

@Getter
@Schema(description = "옵션 상세 정보")
public class FindOptionDetailResponse {

    @Schema(description = "옵션 이름")
    private String name;

    @Schema(description = "상세 설명")
    private String description;

    @Schema(description = "사진 주소")
    private String imgUrl;

    public FindOptionDetailResponse(OptionDetail optionDetail) {
        this.name = optionDetail.getName();
        this.description = optionDetail.getDescription();
        this.imgUrl = optionDetail.getImgUrl();
    }
}
