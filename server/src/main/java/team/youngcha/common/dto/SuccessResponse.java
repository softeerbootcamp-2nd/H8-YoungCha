package team.youngcha.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class SuccessResponse<T> {

    @Schema(description = "요청 결과 메시지")
    private final String message = "success";

    @Schema(description = "요청 데이터")
    private final T data;

    private SuccessResponse() {
        this.data = null;
    }

    public SuccessResponse(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
