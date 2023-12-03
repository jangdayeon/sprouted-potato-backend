package com.hallym.cloud.cloudpotato.dto.bookdetail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditFormResponse {
    private String name;
    private String content;
    private String emoji;

    EditFormResponse() {}
}
