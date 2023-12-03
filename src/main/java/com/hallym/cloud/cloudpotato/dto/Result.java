package com.hallym.cloud.cloudpotato.dto;

import lombok.AllArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private T data;

    public Result() {
    }
}
