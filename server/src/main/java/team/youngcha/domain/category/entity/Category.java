package team.youngcha.domain.category.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Category {

    @Id
    private Long id;

    private String name;

    public Category(String name) {
        this.name = name;
    }
}
