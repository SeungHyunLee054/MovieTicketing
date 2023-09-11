package com.zerobase.exception;

import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> customExceptionHandler(final CustomException e) {
        log.error("error is occurred : {}", e.getErrorCode());
        return ResponseEntity
                .badRequest()
                .body(new ExceptionResponse(e.getMessage(), e.getErrorCode()));
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ExceptionResponse> servletExceptionHandler(final ServletException e) {
        return ResponseEntity
                .badRequest()
                .body(new ExceptionResponse(e.getMessage(), ErrorCode.INVALID_ACCESS));
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class ExceptionResponse {
        private String message;
        private ErrorCode errorCode;
    }
}
