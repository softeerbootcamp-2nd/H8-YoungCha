package team.youngcha.domain.trim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.trim.dto.FindTrimDefaultOptionsResponse;
import team.youngcha.domain.trim.service.TrimService;

@RequestMapping("/trims")
@RestController
@RequiredArgsConstructor
public class TrimController {

    private final TrimService trimService;

    @GetMapping("/{id}/default-components")
    public ResponseEntity<SuccessResponse<FindTrimDefaultOptionsResponse>> findTrimDefaultOptions(
            @PathVariable Long id,
            @RequestParam Long categoryId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        FindTrimDefaultOptionsResponse findTrimDefaultOptionsResponse = trimService.findPaginatedDefaultOptions(id, categoryId, page, size);
        SuccessResponse<FindTrimDefaultOptionsResponse> successResponse = new SuccessResponse<>(findTrimDefaultOptionsResponse);
        return ResponseEntity.ok().body(successResponse);
    }

}
