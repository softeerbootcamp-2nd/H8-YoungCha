package team.youngcha.domain.trim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import team.youngcha.domain.option.dto.ColorOption;
import team.youngcha.domain.option.dto.MainOption;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "트림 상세정보")
public class TrimDetail {

    @Schema(description = "트림 id")
    private final Long id;

    @Schema(description = "트림 이름")
    private final String name;

    @Schema(description = "트림 이미지 주소")
    private final String imgUrl;

    @Schema(description = "트림 배경 이미지 주소")
    private final String backgroundImgUrl;

    @Schema(description = "해시 태그")
    private final String hashTag;

    @Schema(description = "트림 설명")
    private final String description;

    @Schema(description = "베스트 셀러")
    private final boolean best;

    @Schema(description = "트림 최소 가격")
    private final int price;

    @Schema(description = "핵심 옵션")
    private final List<MainOption> mainOptions;

    @Schema(description = "외장 색상 옵션")
    private final List<ColorOption> exteriorColors;

    @Schema(description = "내장 색상 옵션")
    private final List<ColorOption> interiorColors;

    @Builder
    public TrimDetail(Long id, String name, String backgroundImgUrl, String imgUrl, String hashTag, String description, boolean best, int price) {
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
