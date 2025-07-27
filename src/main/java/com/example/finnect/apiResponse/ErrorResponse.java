package com.example.finnect.apiResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ErrorResponse {

    private Boolean isSuccess;
    private String message;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> validation;
}