package team.youngcha.domain.option.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback {

    private String title;
    private String description;

    public Feedback(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
