package com.example.finnect.apiResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {
    private final Boolean isSuccess;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> ApiResponse<T> onSuccess(T result){
        return new ApiResponse<>(true, SuccessStatus._OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> onSuccess(SuccessStatus status, T result) {
        return new ApiResponse<>(true, status.getMessage(), result);
    }

    public static <T> ApiResponse<T> onSuccess(String message) {
        return new ApiResponse<>(true, message, null);
    }

    public static <T> ApiResponse<T> onFailure(String message, T data){
        return new ApiResponse<>(false, message, data);
    }
}
