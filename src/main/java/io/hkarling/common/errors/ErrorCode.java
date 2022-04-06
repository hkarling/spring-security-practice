package io.hkarling.common.errors;

import lombok.Getter;

@Getter
public enum ErrorCode {
    TOKEN_UNAUTHORIZED(1001, "권한 정보가 없는 토큰입니다."),
    TOKEN_EXPIRED(1002, "만료된 JWT 토큰입니다."),
    TOKEN_UNSUPPORTED(1003, "지원되지 않는 JWT 토큰입니다."),
    TOKEN_MALFORMED(1004, "잘못된 JWT 서명입니다."),
    TOKEN_ILLEGAL(1005, "JWT 토큰이 잘못되었습니다."),
    TOKEN_UNMATCHED(1006, "토큰 정보가 일치하지 않습니다."),

    // USER
    USER_NOT_FOUND(1101, "유저를 찾을 수 없습니다."),
    USER_EXISTS(1102, "이미 가입되어 있는 유저입니다."),
    USER_LOGOUT(1103, "로그아웃된 유저입니다."),


    UNIDENTIFIED_ERROR(9999, "알 수 없는 오류");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
