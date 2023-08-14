package team.youngcha.domain.option.entity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option {

    private Long id;
    private String name;
    private int price;
    private String feedback;
    private Long categoryId;

    @Builder
    public Option(Long id, String name, int price, String feedback, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.feedback = feedback;
        this.categoryId = categoryId;
    }
}
