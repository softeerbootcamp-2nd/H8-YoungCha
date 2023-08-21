package team.youngcha.domain.option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.common.enums.AgeRange;
import team.youngcha.common.enums.Gender;
import team.youngcha.domain.category.enums.RequiredCategory;
import team.youngcha.domain.option.dto.FindGuideOptionResponse;
import team.youngcha.domain.option.dto.GuideInfo;
import team.youngcha.domain.option.service.OptionService;

import java.util.List;

@Tag(name = "Option Guide", description = "옵션 가이드 API")
@RestController
@RequestMapping("/car-make/{trimId}/guide")
@RequiredArgsConstructor
public class GuideOptionController {

    private final OptionService optionService;

    @Operation(summary = "파워 트레인 가이드 모드 옵션 조회",
            description = "가이드 모드에서 파워 트레인의 옵션을 유사 사용자 선택량과 함께 조회합니다.")
    @GetMapping("/power-train")
    public ResponseEntity<SuccessResponse<List<FindGuideOptionResponse>>> findGuidePowerTrains(
            @Schema(description = "트림 아이디")
            @PathVariable Long trimId,
            @Schema(description = "성별 (남자: 0, 여자: 1, 선택 안함: 2)")
            @RequestParam Gender gender,
            @Schema(description = "나이 (20대 ~ 70대 이상, 2, 3,..., 7)")
            @RequestParam(name = "age") AgeRange ageRange,
            @Schema(description = "1순위 키워드 아이디")
            @RequestParam Long keyword1Id,
            @Schema(description = "2순위 키워드 아이디")
            @RequestParam Long keyword2Id,
            @Schema(description = "3순위 키워드 아이디")
            @RequestParam Long keyword3Id
    ) {
        GuideInfo guideInfo =
                new GuideInfo(gender, ageRange, List.of(keyword1Id, keyword2Id, keyword3Id));
        return findGuideRequiredOptions(trimId, guideInfo, RequiredCategory.POWER_TRAIN);
    }

    @Operation(summary = "구동 방식 가이드 모드 옵션 조회",
            description = "가이드 모드에서 구동 방식의 옵션을 유사 사용자 선택량과 함께 조회합니다.")
    @GetMapping("/driving-system")
    public ResponseEntity<SuccessResponse<List<FindGuideOptionResponse>>> findGuideDrivingSystem(
            @Schema(description = "트림 아이디")
            @PathVariable Long trimId,
            @Schema(description = "성별 (남자: 0, 여자: 1, 선택 안함: 2)")
            @RequestParam Gender gender,
            @Schema(description = "나이 (20대 ~ 70대 이상, 2, 3,..., 7)")
            @RequestParam(name = "age") AgeRange ageRange,
            @Schema(description = "1순위 키워드 아이디")
            @RequestParam Long keyword1Id,
            @Schema(description = "2순위 키워드 아이디")
            @RequestParam Long keyword2Id,
            @Schema(description = "3순위 키워드 아이디")
            @RequestParam Long keyword3Id
    ) {
        GuideInfo guideInfo =
                new GuideInfo(gender, ageRange, List.of(keyword1Id, keyword2Id, keyword3Id));
        return findGuideRequiredOptions(trimId, guideInfo, RequiredCategory.DRIVING_SYSTEM);
    }

    @Operation(summary = "바디 타입 가이드 모드 옵션 조회",
            description = "가이드 모드에서 바디 타입의 옵션을 유사 사용자 선택량과 함께 조회합니다.")
    @GetMapping("/body-type")
    public ResponseEntity<SuccessResponse<List<FindGuideOptionResponse>>> findGuideBodyType(
            @Schema(description = "트림 아이디")
            @PathVariable Long trimId,
            @Schema(description = "성별 (남자: 0, 여자: 1, 선택 안함: 2)")
            @RequestParam Gender gender,
            @Schema(description = "나이 (20대 ~ 70대 이상, 2, 3,..., 7)")
            @RequestParam(name = "age") AgeRange ageRange,
            @Schema(description = "1순위 키워드 아이디")
            @RequestParam Long keyword1Id,
            @Schema(description = "2순위 키워드 아이디")
            @RequestParam Long keyword2Id,
            @Schema(description = "3순위 키워드 아이디")
            @RequestParam Long keyword3Id
    ) {
        GuideInfo guideInfo = new GuideInfo(gender, ageRange, List.of(keyword1Id, keyword2Id, keyword3Id));
        return findGuideRequiredOptions(trimId, guideInfo, RequiredCategory.BODY_TYPE);
    }

    @Operation(summary = "외장 색상 가이드 모드 옵션 조회",
            description = "가이드 모드에서 외장 색상의 옵션을 연령대 및 성별별 판매율과 함께 조회합니다.")
    @GetMapping("/exterior-color")
    public ResponseEntity<SuccessResponse<List<FindGuideOptionResponse>>> findExteriorColor(
            @Schema(description = "트림 아이디")
            @PathVariable Long trimId,
            @Schema(description = "성별 (남자: 0, 여자: 1, 선택 안함: 2)")
            @RequestParam Gender gender,
            @Schema(description = "나이 (20대 ~ 70대 이상, 2, 3,..., 7)")
            @RequestParam(name = "age") AgeRange ageRange,
            @Schema(description = "1순위 키워드 아이디")
            @RequestParam Long keyword1Id,
            @Schema(description = "2순위 키워드 아이디")
            @RequestParam Long keyword2Id,
            @Schema(description = "3순위 키워드 아이디")
            @RequestParam Long keyword3Id
    ) {
        GuideInfo guideInfo = new GuideInfo(gender, ageRange, List.of(keyword1Id, keyword2Id, keyword3Id));
        List<FindGuideOptionResponse> findGuideOptionResponses = optionService.findGuideModeExteriorColors(trimId, guideInfo);
        SuccessResponse<List<FindGuideOptionResponse>> successResponse = new SuccessResponse<>(findGuideOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "내장 색상 가이드 모드 옵션 조회",
            description = "가이드 모드에서 내장 색상의 옵션을 연령대 및 성별별 판매율과 함께 조회합니다.")
    @GetMapping("/interior-color")
    public ResponseEntity<SuccessResponse<List<FindGuideOptionResponse>>> findInteriorColor(
            @Schema(description = "트림 아이디")
            @PathVariable Long trimId,
            @Schema(description = "성별 (남자: 0, 여자: 1, 선택 안함: 2)")
            @RequestParam Gender gender,
            @Schema(description = "나이 (20대 ~ 70대 이상, 2, 3,..., 7)")
            @RequestParam(name = "age") AgeRange ageRange,
            @Schema(description = "1순위 키워드 아이디")
            @RequestParam Long keyword1Id,
            @Schema(description = "2순위 키워드 아이디")
            @RequestParam Long keyword2Id,
            @Schema(description = "3순위 키워드 아이디")
            @RequestParam Long keyword3Id,
            @Schema(description = "외장 색상 아이디")
            @RequestParam Long exteriorColorId
    ) {
        GuideInfo guideInfo = new GuideInfo(gender, ageRange, List.of(keyword1Id, keyword2Id, keyword3Id));
        List<FindGuideOptionResponse> findGuideOptionResponses = optionService.findGuideModeInteriorColors(trimId, guideInfo, exteriorColorId);
        SuccessResponse<List<FindGuideOptionResponse>> successResponse = new SuccessResponse<>(findGuideOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "휠 가이드 모드 옵션 조회",
            description = "가이드 모드에서 휠의 옵션을 유사 사용자의 선택 비율과 함께 조회합니다.")
    @GetMapping("/wheel")
    public ResponseEntity<SuccessResponse<List<FindGuideOptionResponse>>> findWheel(
            @Schema(description = "트림 아이디")
            @PathVariable Long trimId,
            @Schema(description = "성별 (남자: 0, 여자: 1, 선택 안함: 2)")
            @RequestParam Gender gender,
            @Schema(description = "나이 (20대 ~ 70대 이상, 2, 3,..., 7)")
            @RequestParam(name = "age") AgeRange ageRange,
            @Schema(description = "1순위 키워드 아이디")
            @RequestParam Long keyword1Id,
            @Schema(description = "2순위 키워드 아이디")
            @RequestParam Long keyword2Id,
            @Schema(description = "3순위 키워드 아이디")
            @RequestParam Long keyword3Id,
            @Schema(description = "외장 색상 아이디")
            @RequestParam Long exteriorColorId
    ) {
        GuideInfo guideInfo = new GuideInfo(gender, ageRange, List.of(keyword1Id, keyword2Id, keyword3Id));
        List<FindGuideOptionResponse> findGuideOptionResponses = optionService.findGuideModeWheel(trimId, guideInfo, exteriorColorId);
        SuccessResponse<List<FindGuideOptionResponse>> successResponse = new SuccessResponse<>(findGuideOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "파워 트레인 가이드 모드 옵션 조회",
            description = "가이드 모드에서 파워 트레인의 옵션을 유사 사용자 선택량과 함께 조회합니다.")
    @GetMapping("/options")
    public ResponseEntity<SuccessResponse<List<FindGuideOptionResponse>>> findSelectiveOptions(
            @Schema(description = "트림 아이디")
            @PathVariable Long trimId,
            @Schema(description = "성별 (남자: 0, 여자: 1, 선택 안함: 2)")
            @RequestParam Gender gender,
            @Schema(description = "나이 (20대 ~ 70대 이상, 2, 3,..., 7)")
            @RequestParam(name = "age") AgeRange ageRange,
            @Schema(description = "1순위 키워드 아이디")
            @RequestParam Long keyword1Id,
            @Schema(description = "2순위 키워드 아이디")
            @RequestParam Long keyword2Id,
            @Schema(description = "3순위 키워드 아이디")
            @RequestParam Long keyword3Id
    ) {
        GuideInfo guideInfo =
                new GuideInfo(gender, ageRange, List.of(keyword1Id, keyword2Id, keyword3Id));
        List<FindGuideOptionResponse> findGuideOptionResponses = optionService.findGuideSelectiveOptions(trimId, guideInfo);
        SuccessResponse<List<FindGuideOptionResponse>> successResponse = new SuccessResponse<>(findGuideOptionResponses);
        return ResponseEntity.ok(successResponse);
    }

    private ResponseEntity<SuccessResponse<List<FindGuideOptionResponse>>> findGuideRequiredOptions(Long trimId,
                                                                                                    GuideInfo guideInfo,
                                                                                                    RequiredCategory category) {
        List<FindGuideOptionResponse> findGuideOptionResponses = optionService
                .findGuideRequiredOptions(trimId, guideInfo, category);
        SuccessResponse<List<FindGuideOptionResponse>> successResponse =
                new SuccessResponse<>(findGuideOptionResponses);
        return ResponseEntity.ok(successResponse);
    }
}
