package team.youngcha.domain.option.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
public class Detail {

    @Id
    private Long id;

    private String name;

    private String description;

    @Column("img_url")
    private String imgUrl;
}
