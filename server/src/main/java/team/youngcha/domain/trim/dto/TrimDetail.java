package team.youngcha.domain.trim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngcha.domain.car.dto.TrimBackgroundImgUrl;
import team.youngcha.domain.option.dto.OptionSummary;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "트림 상세정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrimDetail {

    @Schema(description = "트림 id")
    private Long id;

    @Schema(description = "트림 이름")
    private String name;

    @Schema(description = "트림 이미지 주소")
    private String imgUrl;

    @Schema(description = "트림 배경 이미지 주소")
    private TrimBackgroundImgUrl backgroundImgUrl;

    @Schema(description = "해시 태그")
    private String hashTag;

    @Schema(description = "트림 설명")
    private String description;

    @Schema(description = "베스트 셀러")
    private boolean best;

    @Schema(description = "트림 최소 가격")
    private int price;

    @Schema(description = "핵심 옵션")
    private List<OptionSummary> mainOptions;

    @Schema(description = "외장 색상 옵션")
    private List<OptionSummary> exteriorColors;

    @Schema(description = "내장 색상 옵션")
    private List<OptionSummary> interiorColors;

    @Builder
    public TrimDetail(Long id, String name, TrimBackgroundImgUrl backgroundImgUrl, String imgUrl, String hashTag, String description, boolean best, int price) {
        this.id = id;
        this.name = name;
        this.backgroundImgUrl = backgroundImgUrl;
        this.imgUrl = imgUrl;
        this.hashTag = hashTag;
        this.description = description;
        this.best = best;
        this.price = price;
        this.mainOptions = new ArrayList<>();
        this.exteriorColors = new ArrayList<>();
        this.interiorColors = new ArrayList<>();
    }
}
