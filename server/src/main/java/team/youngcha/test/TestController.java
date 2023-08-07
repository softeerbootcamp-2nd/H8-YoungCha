package team.youngcha.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.youngcha.common.dto.ErrorResponse;
import team.youngcha.common.dto.SuccessResponse;
import team.youngcha.common.exception.CustomException;

@Tag(name = "Test API", description = "테스트 API")
@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(summary = "성공 테스트 메소드", description = "임시 성공 메소드입니다.")
    @GetMapping("/ok")
    public ResponseEntity<SuccessResponse<SuccessResponse<String>>> test(
            @Parameter(description = "파라미터")
            @RequestParam(required = false) String param) {
        SuccessResponse<String> response = new SuccessResponse<>("ok");
        SuccessResponse<SuccessResponse<String>> r = new SuccessResponse<>(response);
        return ResponseEntity.ok(r);
    }

    @Operation(summary = "실패 테스트 메소드", description = "임시 실패 메소드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/error")
    public ResponseEntity<SuccessResponse<String>> error() {
        throw new CustomException(HttpStatus.BAD_REQUEST, "error");
    }
}
