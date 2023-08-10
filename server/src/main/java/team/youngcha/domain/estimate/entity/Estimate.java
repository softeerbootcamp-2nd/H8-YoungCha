package team.youngcha.domain.estimate.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
public class Estimate {

    @Id
    private Long id;

    @Column("age_range")
    private int ageRange;

    private int gender;

    @Column("create_date")
    private LocalDateTime createDate;

    public Estimate(int ageRange, int gender, LocalDateTime createDate) {
        this.ageRange = ageRange;
        this.gender = gender;
        this.createDate = createDate;
    }
}
