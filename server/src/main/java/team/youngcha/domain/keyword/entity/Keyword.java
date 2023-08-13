package team.youngcha.domain.keyword.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Keyword {

    private Long id;
    private String name;

    @Builder
    public Keyword(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
