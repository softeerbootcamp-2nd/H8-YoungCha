package com.youngcha.server.common.dto;

public class SuccessResponse<T> {

    private final String message = "success";

    private final T data;

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
