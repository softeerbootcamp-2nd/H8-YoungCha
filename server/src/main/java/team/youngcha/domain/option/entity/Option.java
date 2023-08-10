package team.youngcha.domain.option.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Option {

    private Long id;
    private String name;
    private int price;
    private String feedback;
    private Long categoryId;
}
