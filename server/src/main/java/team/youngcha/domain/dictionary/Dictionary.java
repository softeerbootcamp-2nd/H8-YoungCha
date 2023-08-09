package team.youngcha.domain.dictionary;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Dictionary {
    @Id
    private Long id;

    private String word;

    private String description;

    private String imgUrl;

    public Dictionary(String word, String description, String imgUrl) {
        this.word = word;
        this.description = description;
        this.imgUrl = imgUrl;
    }
}
