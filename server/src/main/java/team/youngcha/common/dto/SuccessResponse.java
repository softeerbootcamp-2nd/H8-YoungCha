package team.youngcha.common.dto;

public class SuccessResponse<T> {

    private final String message = "success";

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
