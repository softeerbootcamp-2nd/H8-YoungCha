package team.youngcha.domain.trim.dto;

import lombok.Builder;
import lombok.Getter;
import team.youngcha.domain.option.dto.ColorOption;
import team.youngcha.domain.option.dto.MainOption;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TrimDetail {
    private final Long id;
    private final String name;
    private final String backgroundImgUrl;
    private final String imgUrl;
    private final String hashTag;
    private final String description;
    private final boolean isBest;
    private final int price;
    private final List<MainOption> mainOptions;
    private final List<ColorOption> exteriorColors;
    private final List<ColorOption> interiorColors;

    @Builder
    public TrimDetail(Long id, String name, String backgroundImgUrl, String imgUrl, String hashTag, String description, int price) {
        this.id = id;
        this.name = name;
        this.backgroundImgUrl = backgroundImgUrl;
        this.imgUrl = imgUrl;
        this.hashTag = hashTag;
        this.description = description;
        this.price = price;
        this.isBest = false;
        this.mainOptions = new ArrayList<>();
        this.exteriorColors = new ArrayList<>();
        this.interiorColors = new ArrayList<>();
    }
}
