package team.youngcha.domain.sell.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
public class Sell {

    @Id
    private Long id;

    private int age;

    private int gender;

    @Column("create_date")
    private LocalDateTime createDate;
}
