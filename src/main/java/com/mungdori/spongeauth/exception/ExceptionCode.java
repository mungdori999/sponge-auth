package com.mungdori.spongeauth.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    BAD_CREATION(400,"계정생성중 문제가 생겼습니다."),
    NOT_AUTHORIZATION(401,"권한이 없습니다"),
    NOT_FOUND_ACCOUNT(404,"계정 정보가 없습니다."),
    NOT_FOUND_ENTITY(404,"조회 정보가 없습니다.");


    private final int code;
    private final String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
