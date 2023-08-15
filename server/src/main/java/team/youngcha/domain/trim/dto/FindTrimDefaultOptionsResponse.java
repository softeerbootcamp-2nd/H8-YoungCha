package team.youngcha.domain.trim.dto;

import lombok.Builder;
import lombok.Getter;
import team.youngcha.domain.option.dto.DefaultOptionSummary;

import java.util.List;

@Getter
public class FindTrimDefaultOptionsResponse {

    private final Long trimId;
    private final boolean first;
    private final boolean last;
    private final int totalElements;
    private final int totalPages;
    private final List<DefaultOptionSummary> contents;

    @Builder
    public FindTrimDefaultOptionsResponse(Long trimId, boolean first, boolean last, int totalElements, int totalPages, List<DefaultOptionSummary> contents) {
        this.trimId = trimId;
        this.first = first;
        this.last = last;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.contents = contents;
    }
}
