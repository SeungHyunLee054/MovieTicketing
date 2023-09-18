package com.zerobase.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    WRONG_ID_OR_PASSWORD(HttpStatus.FORBIDDEN, "아이디 또는 비밀번호가 일치하지 않습니다."),
    NOT_ENOUGH_MONEY(HttpStatus.FORBIDDEN, "잔액이 부족합니다."),
    BLOCKED_USER(HttpStatus.FORBIDDEN, "관리자에 의해 차단된 계정입니다."),
    NO_ADMIN_USER(HttpStatus.FORBIDDEN, "관리자 계정이 아닙니다."),
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),
    NO_EXIST_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."),
    INVALID_ACCESS(HttpStatus.BAD_REQUEST, "유효하지 않은 접근입니다."),
    new_enum(HttpStatus.BAD_REQUEST, "추가된 enum");

    private final HttpStatus httpStatus;
    private final String detail;
}
