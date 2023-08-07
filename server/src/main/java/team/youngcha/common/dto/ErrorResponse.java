package team.youngcha.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorResponse {

    @Schema(description = "요청 결과 메시지")
    private final String message;

    @Schema(description = "오류 상세 메시지", nullable = true)
    private final String detail;

    private ErrorResponse() {
        this.message = null;
        this.detail = null;
    }

    public ErrorResponse(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }
}
