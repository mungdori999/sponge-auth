package com.mungdori.spongeauth.trainer.controller;

import com.mungdori.spongeauth.exception.UpdateException;
import com.mungdori.spongeauth.jwt.JwtUtil;
import com.mungdori.spongeauth.jwt.RefreshRepository;
import com.mungdori.spongeauth.jwt.RefreshToken;
import com.mungdori.spongeauth.jwt.Token;
import com.mungdori.spongeauth.trainer.client.TrainerClient;
import com.mungdori.spongeauth.trainer.dto.TrainerResponse;
import com.mungdori.spongeauth.trainer.dto.TrainerUpdate;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mungdori.spongeauth.utils.LoginType.TRAINER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/trainer")
public class TrainerController {


    private final TrainerClient trainerClient;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @PatchMapping("/{id}")
    public ResponseEntity<RefreshToken> update(@PathVariable Long id, @RequestBody TrainerUpdate trainerUpdate) {
        try {
            TrainerResponse trainerResponse = trainerClient.update(id, trainerUpdate);
            Token token = jwtUtil.createToken(id, trainerResponse.getName(), TRAINER.getLoginType());
            refreshRepository.save(token.getRefreshToken());
            return ResponseEntity.ok().header("Authorization", token.getAccessToken())
                    .body(new RefreshToken(token.getRefreshToken()));
        } catch (FeignException e) {
            throw new UpdateException();
        }


    }
}
