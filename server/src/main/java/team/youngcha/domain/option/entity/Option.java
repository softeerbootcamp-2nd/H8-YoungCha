package team.youngcha.domain.option.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Option {

    @Id
    private Long id;

    private String name;

    private int price;

    private String feedback;

    public Option(String name, int price, String feedback) {
        this.name = name;
        this.price = price;
        this.feedback = feedback;
    }
}
