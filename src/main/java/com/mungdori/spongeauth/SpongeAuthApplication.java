package com.mungdori.spongeauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpongeAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpongeAuthApplication.class, args);
    }

}
