package com.hallym.cloud.cloudpotato.dto.bookdetail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SentimentStatsResponse {
    private double positive;
    private double neutral;
    private double negative;
}
