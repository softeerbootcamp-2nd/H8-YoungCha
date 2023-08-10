package team.youngcha.domain.dictionary.entity;

import lombok.Getter;

@Getter
public class Dictionary {

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
