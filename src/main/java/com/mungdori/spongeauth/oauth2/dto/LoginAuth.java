package com.mungdori.spongeauth.oauth2.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginAuth {
    private Long id;
    private String loginType;
}
