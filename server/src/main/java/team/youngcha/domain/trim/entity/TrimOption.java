package team.youngcha.domain.trim.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class TrimOption {

    @Id
    private Long id;

    private int type;

    public TrimOption(int type) {
        this.type = type;
    }
}
