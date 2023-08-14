package team.youngcha.domain.option.dto;

import lombok.Getter;

@Getter
public class DefaultOptionSummary {
    private final String name;
    private final int categoryId;
    private final String imgUrl;

    public DefaultOptionSummary(String name, int categoryId, String imgUrl) {
        this.name = name;
        this.categoryId = categoryId;
        this.imgUrl = imgUrl;
    }
}
