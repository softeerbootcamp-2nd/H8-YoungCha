package team.youngcha.domain.keyword.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {

    private Long id;
    private String name;

    @Builder
    public Keyword(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
