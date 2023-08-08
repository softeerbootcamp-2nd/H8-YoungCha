package team.youngcha.domain.dictionary;

import lombok.Getter;
import nonapi.io.github.classgraph.json.Id;

@Getter
public class Dictionary {
    @Id
    private Long id;

    private String word;

    private String description;

    private String imgUrl;
}
