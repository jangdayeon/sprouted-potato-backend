package com.hallym.cloud.cloudpotato.dto.bookdetail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditPasswdCheckRequest {
    private long reviewId;
    private String passwd;

    EditPasswdCheckRequest() {}
}
