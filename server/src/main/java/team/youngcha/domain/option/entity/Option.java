package team.youngcha.domain.option.entity;

import lombok.Getter;

@Getter
public class Option {

    private Long id;
    private String name;
    private int price;
    private String feedback;
    private Long categoryId;

    public Option(String name, int price, String feedback, Long categoryId) {
        this.name = name;
        this.price = price;
        this.feedback = feedback;
        this.categoryId = categoryId;
    }
}
