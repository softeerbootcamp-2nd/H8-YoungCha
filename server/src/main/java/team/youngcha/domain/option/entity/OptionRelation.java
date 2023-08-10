package team.youngcha.domain.option.entity;

import lombok.Getter;

@Getter
public class OptionRelation {

    private Long id;
    private Long parentId;
    private Long childId;

    public OptionRelation(Long parentId, Long childId) {
        this.parentId = parentId;
        this.childId = childId;
    }
}
