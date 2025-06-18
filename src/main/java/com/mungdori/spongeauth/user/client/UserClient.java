package com.mungdori.spongeauth.user.client;

import com.mungdori.spongeauth.user.dto.UserCreate;
import com.mungdori.spongeauth.user.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserClient {

    @GetMapping("/api/user")
    UserResponse getByEmail(@RequestParam("email") String email);

    @PostMapping("/api/user")
    UserResponse createUser(@RequestBody UserCreate userCreate);

}
