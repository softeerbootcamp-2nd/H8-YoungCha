package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngcha.domain.keyword.dto.KeywordRate;
import team.youngcha.domain.option.entity.Option;
import team.youngcha.domain.option.entity.OptionDetail;
import team.youngcha.domain.option.entity.OptionImage;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Schema(description = "가이드 모드 조회 응답")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindGuideOptionResponse {

    @Schema(description = "옵션 아이디")
    private Long id;

    @Schema(description = "추천 유무")
    private boolean checked;

    @Schema(description = "유사 사용자 선택 비율")
    private int rate;

    @Schema(description = "추가 가격")
    private int price;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "선택 피드백 문구")
    private String feedback;

    @Schema(description = "태그")
    private List<KeywordRate> tags;

    @Schema(description = "상세 설명")
    private List<FindOptionDetailResponse> details;

    @Schema(description = "사진 정보")
    private List<FindOptionImageResponse> images;

    public FindGuideOptionResponse(Option option, boolean checked, int rate, List<KeywordRate> tags, List<OptionImage> optionImages, List<OptionDetail> optionDetails) {
        this.id = option.getId();
        this.checked = checked;
        this.rate = rate;
        this.price = option.getPrice();
        this.name = option.getName();
        this.feedback = option.getFeedback();
        this.tags = tags;
        this.images = optionImages.stream()
                .map(FindOptionImageResponse::new)
                .collect(Collectors.toList());
        this.details = optionDetails.stream()
                .map(FindOptionDetailResponse::new)
                .collect(Collectors.toList());
    }

    @Builder
    public FindGuideOptionResponse(Long id, boolean checked, int rate, int price, String name, String feedback,
                                   List<KeywordRate> tags,
                                   List<FindOptionDetailResponse> details,
                                   List<FindOptionImageResponse> images) {
        this.id = id;
        this.checked = checked;
        this.rate = rate;
        this.price = price;
        this.name = name;
        this.feedback = feedback;
        this.tags = tags;
        this.details = details;
        this.images = images;
    }
}
