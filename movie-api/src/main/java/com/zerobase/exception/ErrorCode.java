package com.zerobase.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND_FROM_REDIS(HttpStatus.BAD_REQUEST,"redis로 부터 정보를 가져오지 못했습니다."),
    CANT_PUT_TO_REDIS(HttpStatus.BAD_REQUEST,"redis로 정보를 넣지 못했습니다."),
    WRONG_URL(HttpStatus.BAD_REQUEST,"URL이 잘못되어 데이터를 가져오지 못했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
