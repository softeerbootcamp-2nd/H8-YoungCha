package team.youngcha.domain.option.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionRelation {

    private Long id;
    private Long parentId;
    private Long childId;
}
