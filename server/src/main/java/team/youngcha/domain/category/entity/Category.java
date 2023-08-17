package team.youngcha.domain.category.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    private Long id;
    private String name;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
