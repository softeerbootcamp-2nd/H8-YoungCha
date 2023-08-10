package team.youngcha.domain.category.repository;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Category {

    @Id
    private Long id;

    private String name;
}
