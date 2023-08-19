package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Schema(description = "카테고리 아이디")
    private Long categoryId;

    @Schema(description = "선택 비율")
    private int rate;

    @Schema(description = "가격")
    private int price;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "선택 피트백 제목")
    private String feedbackTitle;

    @Schema(description = "선택 피트백 설명")
    private String feedbackDescription;

    @Schema(description = "옵션 사진")
    private List<FindOptionImageResponse> images;

    @Schema(description = "상세 정보")
    private List<FindOptionDetailResponse> details;

    public FindSelfOptionResponse(Option option, int rate, List<OptionImage> optionImages, List<OptionDetail> optionDetails) {
        this.id = option.getId();
        this.categoryId = option.getCategoryId();
        this.rate = rate;
        this.price = option.getPrice();
        this.name = option.getName();
        this.feedbackTitle = option.getFeedbackTitle();
        this.feedbackDescription = option.getFeedbackDescription();
        this.images = optionImages.stream()
                .map(FindOptionImageResponse::new)
                .collect(Collectors.toList());
        this.details = optionDetails.stream()
                .map(FindOptionDetailResponse::new)
                .collect(Collectors.toList());
    }

    @Builder
    public FindSelfOptionResponse(Long id, Long categoryId, int rate, int price, String name,
                                  String feedbackTitle, String feedbackDescription,
                                  List<FindOptionImageResponse> images, List<FindOptionDetailResponse> details) {
        this.id = id;
        this.categoryId = categoryId;
        this.rate = rate;
        this.price = price;
        this.name = name;
        this.feedbackTitle = feedbackTitle;
        this.feedbackDescription = feedbackDescription;
        this.images = images;
        this.details = details;
    }
}
