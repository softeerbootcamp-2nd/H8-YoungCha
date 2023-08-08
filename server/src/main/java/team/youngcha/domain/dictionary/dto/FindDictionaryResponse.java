package team.youngcha.domain.dictionary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import team.youngcha.domain.dictionary.Dictionary;

@Getter
@Schema(description = "백카사전 데이터")
public class FindDictionaryResponse {

    @Schema(description = "용어")
    private final String word;

    @Schema(description = "설명 내용")
    private final String description;

    @Schema(description = "설명 이미지")
    private final String imgUrl;

    public FindDictionaryResponse(final Dictionary dictionary) {
        this.word = dictionary.getWord();
        this.description = dictionary.getDescription();
        this.imgUrl = dictionary.getImgUrl();
    }
}
