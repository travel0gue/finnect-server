package com.example.finnect.apiResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessStatus {

    _OK(HttpStatus.OK,"요청이 성공적으로 처리되었습니다."),
    _CREATED(HttpStatus.CREATED, "요청이 성공적으로 생성되었습니다."),

    SIGN_IN_SUCCESS(HttpStatus.OK, "성공적으로 회원가입되었습니다."),
    USER_LOGOUT_SUCCESS(HttpStatus.OK, "성공적으로 로그아웃되었습니다."),
    USER_EDIT_SUCCESS(HttpStatus.OK, "유저 정보가 성공적으로 변경되었습니다."),
    USER_SIGN_OUT_SUCCESS(HttpStatus.OK, "성공적으로 탈퇴되었습니다."),
    REISSUE_TOKEN_SUCCESS(HttpStatus.OK, "토큰이 성공적으로 재발급되었습니다."),
    USER_INFO_RETRIEVED(HttpStatus.OK, "유저 정보가 조회되었습니다."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "성공적으로 로그인되었습니다."),
    USER_PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "성공적으로 비밀번호가 변경되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
