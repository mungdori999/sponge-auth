package com.mungdori.spongeauth.oauth2.controller;


import com.mungdori.spongeauth.jwt.JwtUtil;
import com.mungdori.spongeauth.jwt.RefreshRepository;
import com.mungdori.spongeauth.jwt.Token;
import com.mungdori.spongeauth.oauth2.controller.response.TrainerOauth2Response;
import com.mungdori.spongeauth.oauth2.controller.response.UserOauth2Response;
import com.mungdori.spongeauth.oauth2.dto.LoginRequest;
import com.mungdori.spongeauth.oauth2.service.KaKaoService;
import com.mungdori.spongeauth.oauth2.service.LoginOAuth2;
import com.mungdori.spongeauth.oauth2.service.OAuth2Service;
import com.mungdori.spongeauth.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
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

//    @PostMapping("/kakao/trainer")
//    public ResponseEntity<TrainerOauth2Response> authTrainerKaKao(@RequestBody LoginRequest loginRequest) {
//        LoginOAuth2 loginOAuth2 = kaKaoService.getKaKaoInfo(loginRequest.getAccessToken());
//        Trainer trainer = oAuth2Service.checkTrainer(loginOAuth2);
//        if (trainer == null) {
//            return ResponseEntity.ok().body(TrainerOauth2Response.register(loginOAuth2, false));
//        } else {
//            Token token = jwtUtil.createToken(trainer.getId(), trainer.getName(), loginRequest.getLoginType());
//            refreshRepository.save(token.getRefreshToken());
//            return ResponseEntity.ok().header("Authorization", token.getAccessToken())
//                    .body(TrainerOauth2Response.login(trainer, true, token.getRefreshToken()));
//        }
//    }
}
