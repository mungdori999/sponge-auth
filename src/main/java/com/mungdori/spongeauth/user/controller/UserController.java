package com.mungdori.spongeauth.user.controller;

import com.mungdori.spongeauth.exception.error.UpdateException;
import com.mungdori.spongeauth.jwt.JwtUtil;
import com.mungdori.spongeauth.jwt.RefreshRepository;
import com.mungdori.spongeauth.jwt.RefreshToken;
import com.mungdori.spongeauth.jwt.Token;
import com.mungdori.spongeauth.user.client.UserClient;
import com.mungdori.spongeauth.user.dto.UserResponse;
import com.mungdori.spongeauth.user.dto.UserUpdate;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mungdori.spongeauth.utils.LoginType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user")
public class UserController {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final UserClient userClient;


    @PatchMapping("/{id}")
    public ResponseEntity<RefreshToken> update(@PathVariable("id") Long id, @RequestBody UserUpdate userUpdate) {
        try {
            UserResponse userResponse = userClient.update(id, userUpdate);
            Token token = jwtUtil.createToken(id, userResponse.getName(), USER.getLoginType());
            refreshRepository.save(token.getRefreshToken());
            return ResponseEntity.ok().header("Authorization", token.getAccessToken())
                    .body(new RefreshToken(token.getRefreshToken()));
        } catch (FeignException e) {
            throw new UpdateException();
        }
    }
}
