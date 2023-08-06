package com.youngcha.server.common.dto;

public class ErrorResponse {

    private final String message;
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
