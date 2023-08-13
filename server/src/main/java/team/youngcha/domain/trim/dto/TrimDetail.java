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
    private final String imgUrl;
    private final String backgroundImgUrl;
    private final String hashTag;
    private final String description;
    private final boolean best;
    private final int price;
    private final List<MainOption> mainOptions;
    private final List<ColorOption> exteriorColors;
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
