package team.youngcha.domain.trim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.youngcha.domain.option.dto.DefaultOptionSummary;

import java.util.List;

@Schema(description = "트림 기본 품목 응답 페이지")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindTrimDefaultOptionsResponse {

    @Schema(description = "트림 아이디")
    private Long trimId;

    @Schema(description = "첫번째 페이지이면 true")
    private boolean first;

    @Schema(description = "마지막 페이지이면 true")
    private boolean last;

    @Schema(description = "전체 데이터 개수")
    private int totalElements;

    @Schema(description = "전체 페이지 수")
    private int totalPages;

    @Schema(description = "기본 품목 정보")
    private List<DefaultOptionSummary> contents;

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
