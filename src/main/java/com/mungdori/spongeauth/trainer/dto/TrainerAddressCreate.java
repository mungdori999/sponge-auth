package com.mungdori.spongeauth.trainer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrainerAddressCreate {

    private String city;
    private String town;
}
