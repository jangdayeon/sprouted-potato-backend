package com.hallym.cloud.cloudpotato.dto.bookdetail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmojiCountResponse {
    private long emoji1;
    private long emoji2;
    private long emoji3;
    private long emoji4;
    private long emoji5;
    private long emoji6;
    private long emoji7;

    EmojiCountResponse() {}
}
