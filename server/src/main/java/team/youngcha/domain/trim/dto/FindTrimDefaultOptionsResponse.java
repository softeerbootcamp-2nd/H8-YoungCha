package team.youngcha.domain.trim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import team.youngcha.domain.option.dto.DefaultOptionSummary;

import java.util.List;

@Schema(description = "트림 기본 품목 응답 페이지")
@Getter
public class FindTrimDefaultOptionsResponse {

    @Schema(description = "트림 아이디")
    private final Long trimId;

    @Schema(description = "첫번째 페이지이면 true")
    private final boolean first;

    @Schema(description = "마지막 페이지이면 true")
    private final boolean last;

    @Schema(description = "전체 데이터 개수")
    private final int totalElements;

    @Schema(description = "전체 페이지 수")
    private final int totalPages;

    @Schema(description = "기본 품목 정보")
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
