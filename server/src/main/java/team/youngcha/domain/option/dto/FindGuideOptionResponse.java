package team.youngcha.domain.option.dto;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindGuideOptionResponse {

    private Long id;
    private boolean isChecked;
    private int rate;
    private int price;
    private String name;
    private List<KeywordRate> tags;
    private List<FindOptionDetailResponse> details;
    private List<FindOptionImageResponse> images;

    public FindGuideOptionResponse(Option option, boolean isChecked, int rate, List<KeywordRate> tags, List<OptionImage> optionImages, List<OptionDetail> optionDetails) {
        this.id = option.getId();
        this.isChecked = isChecked;
        this.rate = rate;
        this.price = option.getPrice();
        this.name = option.getName();
        this.tags = tags;
        this.images = optionImages.stream()
                .map(FindOptionImageResponse::new)
                .collect(Collectors.toList());
        this.details = optionDetails.stream()
                .map(FindOptionDetailResponse::new)
                .collect(Collectors.toList());
    }

    @Builder
    public FindGuideOptionResponse(Long id, boolean isChecked, int rate, int price, String name, List<KeywordRate> tags,
                                   List<FindOptionDetailResponse> details, List<FindOptionImageResponse> images) {
        this.id = id;
        this.isChecked = isChecked;
        this.rate = rate;
        this.price = price;
        this.name = name;
        this.tags = tags;
        this.details = details;
        this.images = images;
    }
}
