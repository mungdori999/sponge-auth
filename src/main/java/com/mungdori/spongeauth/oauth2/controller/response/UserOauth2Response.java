package com.mungdori.spongeauth.oauth2.controller.response;

import com.mungdori.spongeauth.user.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserOauth2Response {

    private Long id;
    private String name;
    private String refreshToken;

    public static UserOauth2Response from(UserResponse user, String refreshToken) {
        return UserOauth2Response.builder()
                .id(user.getId())
                .name(user.getName())
                .refreshToken(refreshToken)
                .build();
    }
}
