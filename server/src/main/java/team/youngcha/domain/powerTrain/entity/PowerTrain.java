package team.youngcha.domain.powerTrain.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class PowerTrain {

    @Id
    private Long id;

    private String name;

    private String feedback;
}
