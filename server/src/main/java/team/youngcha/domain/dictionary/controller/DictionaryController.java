package team.youngcha.domain.dictionary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.domain.dictionary.dto.FindDictionaryResponse;
import team.youngcha.domain.dictionary.service.DictionaryService;

import java.util.List;

@Tag(name = "Dictionary", description = "백카사전 API")
@RestController
@RequestMapping("/car-make/dictionary")
@RequiredArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Operation(summary = "백카사전 전체 조회", description = "백카사전의 전체 데이터를 조회합니다.")
    @GetMapping
    public ResponseEntity<SuccessResponse<List<FindDictionaryResponse>>> findDictionary() {
        List<FindDictionaryResponse> dictionary = dictionaryService.findDictionary();
        SuccessResponse<List<FindDictionaryResponse>> successResponse = new SuccessResponse<>(dictionary);
        return ResponseEntity.ok(successResponse);
    }
}
