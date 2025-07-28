package com.example.finnect.apiResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorStatus {

    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러, 관리자에게 문의 바랍니다."),
    _GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "서버 에러, 관리자에게 문의 바랍니다."),

    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "금지된 요청입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없는 요청입니다."),
    _LOGIN_FAILURE(HttpStatus.UNAUTHORIZED, "로그인을 실패했습니다."),

    DUPLICATED_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public ErrorResponse getReason(){
        return ErrorResponse.builder()
                .message(message)
                .isSuccess(false)
                .build();
    }
}
