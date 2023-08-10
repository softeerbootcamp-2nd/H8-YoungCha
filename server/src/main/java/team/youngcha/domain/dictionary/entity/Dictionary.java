package team.youngcha.domain.dictionary.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Dictionary {

    private Long id;
    private String word;
    private String description;
    private String imgUrl;
}
