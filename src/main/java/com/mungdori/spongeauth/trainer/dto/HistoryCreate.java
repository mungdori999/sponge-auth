package com.mungdori.spongeauth.trainer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HistoryCreate {
    private String title;
    private String startDt;
    private String endDt;
    private String description;
}
