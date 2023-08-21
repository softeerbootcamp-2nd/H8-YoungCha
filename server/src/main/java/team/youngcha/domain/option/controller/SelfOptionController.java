package team.youngcha.domain.option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.category.enums.RequiredCategory;
import team.youngcha.domain.option.dto.FindSelfOptionResponse;
import team.youngcha.domain.option.service.OptionService;

import java.util.List;

@Tag(name = "Option Self", description = "옵션 셀프 모드 API")
@RestController
@RequestMapping("/car-make/{trimId}/self")
@RequiredArgsConstructor
public class SelfOptionController {

    private final OptionService optionService;

    @Operation(summary = "셀프 모드 - 파워 트레인 옵션 조회", 
            description = "셀프 모드에서 파워 트레인 옵션을 판매율과 함께 조회합니다.")
    @GetMapping("/power-train")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findPowerTrains(
            @PathVariable Long trimId
    ) {
        return findRequiredOptions(trimId, RequiredCategory.POWER_TRAIN);
    }

    @Operation(summary = "셀프 모드 - 구동 방식 옵션 조회", 
            description = "셀프 모드에서 구동 방식 옵션을 판매율과 함께 조회합니다.")
    @GetMapping("/driving-system")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findDrivingSystem(
            @PathVariable Long trimId
    ) {
        return findRequiredOptions(trimId, RequiredCategory.DRIVING_SYSTEM);
    }

    @Operation(summary = "셀프 모드 - 바디 타입 옵션 조회", 
            description = "셀프 모드에서 바디 타입 옵션을 판매율과 함께 조회합니다.")
    @GetMapping("/body-type")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findBodyType(
            @PathVariable Long trimId
    ) {
        List<FindSelfOptionResponse> findSelfOptionResponses = optionService
                .findSelfRequiredOptions(trimId, RequiredCategory.BODY_TYPE);
        SuccessResponse<List<FindSelfOptionResponse>> successResponse = new SuccessResponse<>(findSelfOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "셀프 모드 - 외장 색상 옵션 조회",
            description = "셀프 모드에서 외장 색상 옵션을 판매율과 함께 조회합니다.")
    @GetMapping("/exterior-color")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findExteriorColor(
            @PathVariable Long trimId
    ) {
        return findRequiredOptions(trimId, RequiredCategory.EXTERIOR_COLOR);
    }

    @Operation(summary = "가이 모드 - 내장 색상 옵션 조회",
            description = "셀프 모드에서 내장 색상 옵션을 판매율과 함께 조회합니다.")
    @GetMapping("/interior-color")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findInteriorColor(
            @PathVariable Long trimId,
            @Schema(description = "외장 색상 아이디")
            @RequestParam Long exteriorColorId
    ) {
        List<FindSelfOptionResponse> findSelfOptionResponses = optionService
                .findSelfInteriorColors(trimId, exteriorColorId);
        SuccessResponse<List<FindSelfOptionResponse>> successResponse = new SuccessResponse<>(findSelfOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "셀프 모드 - 휠 옵션 조회",
            description = "셀프 모드에서 휠 옵션을 판매율과 함께 조회합니다.")
    @GetMapping("/wheel")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findWheel(@PathVariable Long trimId) {
        return findRequiredOptions(trimId, RequiredCategory.WHEEL);
    }

    @Operation(summary = "셀프 모드 - 선택 옵션 조회",
            description = "셀프 모드에서 선택 옵션을 판매율과 함께 조회합니다.")
    @GetMapping("/options")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findSelectiveOptions(@PathVariable Long trimId) {
        List<FindSelfOptionResponse> findSelfOptionResponses = optionService.findSelfSelectiveOptions(trimId);
        SuccessResponse<List<FindSelfOptionResponse>> successResponse = new SuccessResponse<>(findSelfOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

    private ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findRequiredOptions(Long trimId, RequiredCategory category) {
        List<FindSelfOptionResponse> findSelfOptionResponses = optionService.findSelfRequiredOptions(trimId, category);
        SuccessResponse<List<FindSelfOptionResponse>> successResponse = new SuccessResponse<>(findSelfOptionResponses);
        return ResponseEntity.ok(successResponse);
    }
}
