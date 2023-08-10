package team.youngcha.domain.keyword.entity;

import lombok.Getter;

@Getter
public class Keyword {

    private Long id;
    private String name;

    public Keyword(String name) {
        this.name = name;
    }
}
