package team.youngcha.domain.trim.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.trim.dto.FindTrimDefaultOptionsResponse;
import team.youngcha.domain.trim.service.TrimService;

@Tag(name = "Trim", description = "트림 API")
@RequestMapping("/trims")
@RestController
@RequiredArgsConstructor
public class TrimController {

    private final TrimService trimService;

    @Operation(summary = "트림 기본 품목 조회", description = "트림의 기본 품목들을 페이지 단위로 조회합니다.")
    @GetMapping("/{id}/default-components")
    public ResponseEntity<SuccessResponse<FindTrimDefaultOptionsResponse>> findTrimDefaultOptions(
            @Parameter(description = "트림 아이디", in = ParameterIn.PATH)
            @PathVariable Long id,

            @Parameter(description = "카테고리 아이디", in = ParameterIn.QUERY)
            @RequestParam Long categoryId,

            @Parameter(description = "페이지 번호", in = ParameterIn.QUERY)
            @RequestParam int page,

            @Parameter(description = "페이지 크기", in = ParameterIn.QUERY)
            @RequestParam int size
    ) {
        FindTrimDefaultOptionsResponse findTrimDefaultOptionsResponse = trimService.findPaginatedDefaultOptions(id, categoryId, page, size);
        SuccessResponse<FindTrimDefaultOptionsResponse> successResponse = new SuccessResponse<>(findTrimDefaultOptionsResponse);
        return ResponseEntity.ok().body(successResponse);
    }

}
