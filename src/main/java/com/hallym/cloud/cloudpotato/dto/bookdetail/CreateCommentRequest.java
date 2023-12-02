package com.hallym.cloud.cloudpotato.dto.bookdetail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCommentRequest {
    private String name;
    private String content;
    private String emoji;
    private String isbn;
    private String reviewPw;

    CreateCommentRequest() {}
}
