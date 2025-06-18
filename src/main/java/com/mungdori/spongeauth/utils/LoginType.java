package com.mungdori.spongeauth.utils;

import lombok.Getter;

@Getter
public enum LoginType {

    TRAINER("trainer"), USER("user");

    private final String loginType;

    LoginType(String loginType) {
        this.loginType = loginType;
    }

}
