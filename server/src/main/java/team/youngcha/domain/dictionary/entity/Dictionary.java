package team.youngcha.domain.dictionary.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dictionary {

    private Long id;
    private String word;
    private String description;
    private String imgUrl;

    @Builder
    public Dictionary(Long id, String word, String description, String imgUrl) {
        this.id = id;
        this.word = word;
        this.description = description;
        this.imgUrl = imgUrl;
    }
}
