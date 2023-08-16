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
        List<FindSelfOptionResponse> findSelfOptionResponses = optionService
                .findSelfOptions(trimId, SelectiveCategory.POWER_TRAIN);
        SuccessResponse<List<FindSelfOptionResponse>> successResponse = new SuccessResponse<>(findSelfOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "구동 방식 셀프 모드 옵션 조회", description = "셀프 모드에서 구동 방식의 옵션을 판매량과 함께 조회합니다.")
    @GetMapping("/driving-system")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findSelfDrivingSystem(
            @PathVariable Long trimId
    ) {
        List<FindSelfOptionResponse> findSelfOptionResponses = optionService
                .findSelfOptions(trimId, SelectiveCategory.DRIVING_SYSTEM);
        SuccessResponse<List<FindSelfOptionResponse>> successResponse = new SuccessResponse<>(findSelfOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "바디 타입 셀프 모드 옵션 조회", description = "셀프 모드에서 바디 타입의 옵션을 판매량과 함께 조회합니다.")
    @GetMapping("/body-type")
    public ResponseEntity<SuccessResponse<List<FindSelfOptionResponse>>> findSelfBodyType(
            @PathVariable Long trimId
    ) {
        List<FindSelfOptionResponse> findSelfOptionResponses = optionService
                .findSelfOptions(trimId, SelectiveCategory.BODY_TYPE);
        SuccessResponse<List<FindSelfOptionResponse>> successResponse = new SuccessResponse<>(findSelfOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

}
