package team.youngcha.domain.category.entity;

import lombok.Getter;

@Getter
public class Category {

    private Long id;
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
