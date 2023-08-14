package team.youngcha.domain.option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.option.dto.FindSelfOptionResponse;
import team.youngcha.domain.option.service.OptionService;

import java.util.List;

@Tag(name = "Option", description = "옵션 API")
@RestController
@RequestMapping("/car-make/{trimId}")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @Operation(summary = "파워 트레인 셀프 모드 옵션 조회", description = "셀프 모드에서 파워 트레인의 옵션을 판매량과 함께 조회합니다.")
    @GetMapping("/self/power-train")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findSelfPowerTrains(
            @PathVariable Long trimId
    ) {
        List<FindSelfOptionResponse> findSelfOptionResponses = optionService.findSelfPowerTrains(trimId);
        SuccessResponse<List<FindSelfOptionResponse>> successResponse =
                new SuccessResponse<>(findSelfOptionResponses);
        return ResponseEntity.ok(successResponse);
    }
}
