package com.mungdori.spongeauth.trainer.controller;

import com.mungdori.spongeauth.trainer.dto.TrainerUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/trainer")
public class TrainerController {


    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, TrainerUpdate trainerUpdate) {



    }
}
