package com.hallym.cloud.cloudpotato.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
public class ReviewInfo {

    @Id
    @GeneratedValue
    private Long reviewId;
    private String userName;
    private LocalDateTime reviewDate;
    private String reviewPw;
    private String isbn;
    private String content;
    private String emoji;
    private String resultAI;

    // 수정 메서드
    public void change(String content, String emoji, String resultAI) {
        this.content = content;
        this.emoji = emoji;
        this.resultAI = resultAI;
    }
}
