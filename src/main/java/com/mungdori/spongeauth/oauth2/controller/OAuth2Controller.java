package com.mungdori.spongeauth.oauth2.controller;


import com.mungdori.spongeauth.jwt.JwtUtil;
import com.mungdori.spongeauth.jwt.RefreshRepository;
import com.mungdori.spongeauth.jwt.RefreshToken;
import com.mungdori.spongeauth.jwt.Token;
import com.mungdori.spongeauth.oauth2.controller.response.TrainerOauth2Response;
import com.mungdori.spongeauth.oauth2.controller.response.UserOauth2Response;
import com.mungdori.spongeauth.oauth2.dto.LoginRequest;
import com.mungdori.spongeauth.oauth2.service.KaKaoService;
import com.mungdori.spongeauth.oauth2.dto.LoginOAuth2;
import com.mungdori.spongeauth.oauth2.service.OAuth2Service;
import com.mungdori.spongeauth.trainer.dto.TrainerCreate;
import com.mungdori.spongeauth.trainer.dto.TrainerResponse;
import com.mungdori.spongeauth.user.dto.UserResponse;
import com.mungdori.spongeauth.utils.LoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;
    private final KaKaoService kaKaoService;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;


    @PostMapping("/kakao/user")
    public ResponseEntity<UserOauth2Response> authUserKaKao(@RequestBody LoginRequest loginRequest) {
        LoginOAuth2 loginOAuth2 = kaKaoService.getKaKaoInfo(loginRequest.getAccessToken());
        UserResponse user = oAuth2Service.getOrCreateUser(loginOAuth2);
        Token token = jwtUtil.createToken(user.getId(), user.getName(), loginRequest.getLoginType());
        refreshRepository.save(token.getRefreshToken());
        return ResponseEntity.ok().header("Authorization", token.getAccessToken())
                .body(UserOauth2Response.from(user, token.getRefreshToken()));

    }

    /**
     * 훈련사 카카오 로그인
     * @param loginRequest
     * @return
     */
    @PostMapping("/kakao/trainer")
    public ResponseEntity<TrainerOauth2Response> authTrainerKaKao(@RequestBody LoginRequest loginRequest) {
        LoginOAuth2 loginOAuth2 = kaKaoService.getKaKaoInfo(loginRequest.getAccessToken());
        Optional<TrainerResponse> trainer = oAuth2Service.getTrainer(loginOAuth2);
        if (trainer.isEmpty()) {
            return ResponseEntity.ok().body(TrainerOauth2Response.register(loginOAuth2, false));
        } else {
            TrainerResponse trainerResponse = trainer.get();
            Token token = jwtUtil.createToken(trainerResponse.getId(), trainerResponse.getName(), loginRequest.getLoginType());
            refreshRepository.save(token.getRefreshToken());
            return ResponseEntity.ok().header("Authorization", token.getAccessToken())
                    .body(TrainerOauth2Response.login(trainerResponse, true, token.getRefreshToken()));
        }
    }

    /**
     * 훈련사 가입
     * @param trainerCreate
     * @return
     */
    @PostMapping("/trainer")
    public ResponseEntity<TrainerOauth2Response> createTrainer(@RequestBody TrainerCreate trainerCreate) {
        TrainerResponse trainerResponse = oAuth2Service.saveTrainer(trainerCreate);
        Token token = jwtUtil.createToken(trainerResponse.getId(), trainerResponse.getName(), LoginType.TRAINER.getLoginType());
        refreshRepository.save(token.getRefreshToken());
        return ResponseEntity.ok().header("Authorization", token.getAccessToken())
                .body(TrainerOauth2Response.login(trainerResponse,true,token.getRefreshToken()));
    }

    @PostMapping("/logout")
    public void logout(@RequestBody RefreshToken refreshToken) {
        refreshRepository.deleteByRefresh(refreshToken.getRefreshToken());
    }


}
