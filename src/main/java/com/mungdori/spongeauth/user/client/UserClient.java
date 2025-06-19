package com.mungdori.spongeauth.user.client;

import com.mungdori.spongeauth.user.dto.UserCreate;
import com.mungdori.spongeauth.user.dto.UserResponse;
import com.mungdori.spongeauth.user.dto.UserUpdate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserClient {

    @GetMapping("/api/user")
    UserResponse getByEmail(@RequestParam("email") String email);

    @PostMapping("/api/user")
    UserResponse create(@RequestBody UserCreate userCreate);

    @PatchMapping("/api/user/{id}")
    UserResponse update(@PathVariable Long id, @RequestBody UserUpdate userUpdate);

}
