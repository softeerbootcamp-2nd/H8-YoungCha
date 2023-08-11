package team.youngcha.domain.option.dto;

import lombok.Getter;
import team.youngcha.domain.option.entity.Option;
import team.youngcha.domain.option.entity.OptionDetail;
import team.youngcha.domain.option.entity.OptionImage;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FindSelfOptionResponse {

    private Long id;
    private int rate;
    private int price;
    private String name;
    private List<FindOptionImageResponse> images;
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
