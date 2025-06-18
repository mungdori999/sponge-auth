package com.mungdori.spongeauth.trainer.client;

import com.mungdori.spongeauth.trainer.dto.TrainerCreate;
import com.mungdori.spongeauth.trainer.dto.TrainerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "trainer-service", url = "http://localhost:8081")
public interface TrainerClient {


    @GetMapping("/api/trainer")
    TrainerResponse getByEmail(@RequestParam("email") String email);

    @PostMapping("/api/trainer")
    TrainerResponse save(@RequestBody TrainerCreate trainerCreate);

}
