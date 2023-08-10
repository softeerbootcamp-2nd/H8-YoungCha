package team.youngcha.domain.keyword.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Keyword {

    @Id
    private Long id;

    private String name;
}
