package team.youngcha.domain.option.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
public class OptionImage {

    @Id
    private Long id;

    @Column("img_url")
    private String imgUrl;

    @Column("img_type")
    private int imgType;

    public OptionImage(String imgUrl, int imgType) {
        this.imgUrl = imgUrl;
        this.imgType = imgType;
    }
}
