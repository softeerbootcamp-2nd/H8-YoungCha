package team.youngcha.domain.dictionary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngcha.domain.dictionary.entity.Dictionary;

@Getter
@NoArgsConstructor
@Schema(description = "백카사전 데이터")
public class FindDictionaryResponse {

    @Schema(description = "용어")
    private String word;

    @Schema(description = "설명 내용")
    private String description;

    @Schema(description = "설명 이미지")
    private String imgUrl;

    public FindDictionaryResponse(String word, String description, String imgUrl) {
        this.word = word;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public FindDictionaryResponse(final Dictionary dictionary) {
        this.word = dictionary.getWord();
        this.description = dictionary.getDescription();
        this.imgUrl = dictionary.getImgUrl();
    }
}
