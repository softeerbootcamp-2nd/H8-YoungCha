package team.youngcha.domain.option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.category.enums.SelectiveCategory;
import team.youngcha.domain.option.dto.FindSelfOptionResponse;
import team.youngcha.domain.option.service.OptionService;

import java.util.List;

@Tag(name = "Option Self", description = "옵션 셀프 모드 API")
@RestController
@RequestMapping("/car-make/{trimId}/self")
@RequiredArgsConstructor
public class SelfOptionController {

    private final OptionService optionService;

    @Operation(summary = "파워 트레인 셀프 모드 옵션 조회", description = "셀프 모드에서 파워 트레인의 옵션을 판매량과 함께 조회합니다.")
    @GetMapping("/power-train")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findSelfPowerTrains(
            @PathVariable Long trimId
    ) {
        return findSelfOptions(trimId, SelectiveCategory.POWER_TRAIN);
    }

    @Operation(summary = "구동 방식 셀프 모드 옵션 조회", description = "셀프 모드에서 구동 방식의 옵션을 판매량과 함께 조회합니다.")
    @GetMapping("/driving-system")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findSelfDrivingSystem(
            @PathVariable Long trimId
    ) {
        return findSelfOptions(trimId, SelectiveCategory.DRIVING_SYSTEM);
    }

    @Operation(summary = "바디 타입 셀프 모드 옵션 조회", description = "셀프 모드에서 바디 타입의 옵션을 판매량과 함께 조회합니다.")
    @GetMapping("/body-type")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findSelfBodyType(
            @PathVariable Long trimId
    ) {
        return findSelfOptions(trimId, SelectiveCategory.EXTERIOR_COLOR);
    }

    @Operation(summary = "내장 색상 셀프 모드 옵션 조회", description = "셀프 모드에서 내장 색상의 옵션을 판매량과 함께 조회합니다.")
    @GetMapping("/interior-color")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findSelfExteriorColor(
            @PathVariable Long trimId,
            @Schema(description = "외장 색상 아이디")
            @RequestParam Long exteriorColorId
    ) {
        List<FindSelfOptionResponse> findSelfOptionResponses = optionService
                .findSelfInteriorColors(trimId, exteriorColorId);
        SuccessResponse<List<FindSelfOptionResponse>> successResponse = new SuccessResponse<>(findSelfOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

    private ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findSelfOptions(Long trimId, SelectiveCategory category) {
        List<FindSelfOptionResponse> findSelfOptionResponses = optionService.findSelfOptions(trimId, category);
        SuccessResponse<List<FindSelfOptionResponse>> successResponse = new SuccessResponse<>(findSelfOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

}
