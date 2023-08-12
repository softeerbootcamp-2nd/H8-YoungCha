package team.youngcha.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngcha.domain.option.entity.Spec;

@Getter
@Schema(description = "옵션 스펙")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindSpecResponse {

    @Schema(description = "이름")
    private String name;

    @Schema(description = "내용")
    private String description;

    public FindSpecResponse(Spec spec) {
        this.name = spec.getName();
        this.description = spec.getDescription();
    }
}
