package team.youngcha.domain.dictionary.entity;

import lombok.*;

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
