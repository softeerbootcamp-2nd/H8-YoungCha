package team.youngcha.domain.option.entity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option {

    private Long id;
    private String name;
    private int price;
    private String feedbackTitle;
    private String feedbackDescription;
    private Long categoryId;

    @Builder
    public Option(Long id, String name, int price,
                  String feedbackTitle, String feedbackDescription, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.feedbackTitle = feedbackTitle;
        this.feedbackDescription = feedbackDescription;
        this.categoryId = categoryId;
    }
}
