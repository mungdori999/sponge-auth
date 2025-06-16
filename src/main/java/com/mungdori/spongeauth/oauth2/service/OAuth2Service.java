package com.mungdori.spongeauth.oauth2.service;

import com.mungdori.spongeauth.exception.CreateException;
import com.mungdori.spongeauth.user.client.UserClient;
import com.mungdori.spongeauth.user.dto.UserCreate;
import com.mungdori.spongeauth.user.dto.UserResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final UserClient userClient;

    public UserResponse getOrCreateUser(LoginOAuth2 loginOAuth2) {
        try {
            return userClient.getByEmail(loginOAuth2.getEmail());
        } catch (FeignException.NotFound e) {
            UserCreate user = UserCreate.builder()
                    .name(loginOAuth2.getName())
                    .email(loginOAuth2.getEmail()).build();
            try {
                return userClient.createUser(user);
            } catch (FeignException ex) {
                throw new CreateException();
            }
        }
    }

//    public Trainer checkTrainer(LoginOAuth2 loginOAuth2) {
//        Trainer existData = trainerRepository.findByEmail(loginOAuth2.getEmail()).orElse(null);
//        return existData;
//    }
}
