package com.mungdori.spongeauth.oauth2.service;

import com.mungdori.spongeauth.exception.CreateException;
import com.mungdori.spongeauth.oauth2.dto.LoginOAuth2;
import com.mungdori.spongeauth.trainer.client.TrainerClient;
import com.mungdori.spongeauth.trainer.dto.TrainerCreate;
import com.mungdori.spongeauth.trainer.dto.TrainerResponse;
import com.mungdori.spongeauth.user.client.UserClient;
import com.mungdori.spongeauth.user.dto.UserCreate;
import com.mungdori.spongeauth.user.dto.UserResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final UserClient userClient;
    private final TrainerClient trainerClient;

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

    public Optional<TrainerResponse> getTrainer(LoginOAuth2 loginOAuth2) {
        try {
            return Optional.ofNullable(trainerClient.getByEmail(loginOAuth2.getEmail()));
        }
        catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }


    public TrainerResponse saveTrainer(TrainerCreate trainerCreate) {
        try {
            return trainerClient.save(trainerCreate);
        }
        catch (FeignException e) {
            throw new CreateException();
        }
    }
}
