package com.hallym.cloud.cloudpotato.dto.bookdetail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditCommentRequest {
    private String passwd;
    private String content;
    private String emoji;

    EditCommentRequest() {}
}
