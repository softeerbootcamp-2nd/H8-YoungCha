package team.youngcha.domain.trim.dto;

import lombok.Builder;
import lombok.Getter;
import team.youngcha.domain.option.dto.ColorOption;
import team.youngcha.domain.option.dto.MainOption;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TrimDetail {
    private Long id;
    private String name;
    private String backgroundImgUrl;
    private String imgUrl;
    private String hashTag;
    private String description;
    private boolean isBest;
    private int price;
    private List<MainOption> mainOptions;
    private List<ColorOption> exteriorColors;
    private List<ColorOption> interiorColors;

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
