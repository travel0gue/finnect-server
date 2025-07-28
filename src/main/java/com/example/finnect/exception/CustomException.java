package com.example.finnect.exception;

import com.example.finnect.apiResponse.ErrorStatus;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorStatus errorStatus;

    public CustomException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }
}
