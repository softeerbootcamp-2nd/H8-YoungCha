package team.youngcha.domain.sell.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
public class Sell {

    @Id
    private Long id;

    private int age;

    private int gender;

    private LocalDateTime createDate;
}
