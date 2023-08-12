package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngcha.domain.option.entity.Option;
import team.youngcha.domain.option.entity.OptionDetail;
import team.youngcha.domain.option.entity.OptionImage;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Schema(description = "셀프 모드 옵션")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindSelfOptionResponse {

    @Schema(description = "옵션 아이디")
    private Long id;

    @Schema(description = "선택 비율")
    private int rate;

    @Schema(description = "가격")
    private int price;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "옵션 사진")
    private List<FindOptionImageResponse> images;

    @Schema(description = "상세 정보")
    private List<FindOptionDetailResponse> details;

    public FindSelfOptionResponse(Option option, int rate, List<OptionImage> optionImages, List<OptionDetail> optionDetails) {
        this.id = option.getId();
        this.rate = rate;
        this.price = option.getPrice();
        this.name = option.getName();
        this.images = optionImages.stream()
                .map(FindOptionImageResponse::new)
                .collect(Collectors.toList());
        this.details = optionDetails.stream()
                .map(FindOptionDetailResponse::new)
                .collect(Collectors.toList());
    }
}
