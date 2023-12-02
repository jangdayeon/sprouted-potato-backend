package com.hallym.cloud.cloudpotato.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BooksFindDto {
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private String cover;
    BooksFindDto() {}
}
