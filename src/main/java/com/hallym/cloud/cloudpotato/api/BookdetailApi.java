package com.hallym.cloud.cloudpotato.api;

import com.hallym.cloud.cloudpotato.domain.ReviewInfo;
import com.hallym.cloud.cloudpotato.dto.Result;
import com.hallym.cloud.cloudpotato.dto.bookdetail.*;
import com.hallym.cloud.cloudpotato.service.ReviewInfoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookdetailApi {

    private final ReviewInfoService reviewInfoService;
    private final ClovaSentiment clovaSentiment;

    @GetMapping("/bookdetail/{isbn}")
    public EmojiCountResponse emojiCountApi(@PathVariable("isbn") String isbn) {
        long emoji1Count = reviewInfoService.countByIsbnEmoji(isbn, "😄");
        long emoji2Count = reviewInfoService.countByIsbnEmoji(isbn, "😭");
        long emoji3Count = reviewInfoService.countByIsbnEmoji(isbn, "🥹");
        long emoji4Count = reviewInfoService.countByIsbnEmoji(isbn, "🥱");
        long emoji5Count = reviewInfoService.countByIsbnEmoji(isbn, "😡");
        long emoji6Count = reviewInfoService.countByIsbnEmoji(isbn, "😔");
        long emoji7Count = reviewInfoService.countByIsbnEmoji(isbn, "😍");

        return new EmojiCountResponse(emoji1Count, emoji2Count, emoji3Count, emoji4Count, emoji5Count, emoji6Count, emoji7Count);
    }

    @PostMapping("/bookdetail/new")
    public CommentResponse commentNew(@RequestBody CreateCommentRequest request) {

        LocalDateTime create_date = LocalDateTime.now().withNano(0);

        ReviewInfo reviewInfo = new ReviewInfo(request.getName(), create_date, request.getReviewPw(), request.getIsbn(),
                request.getContent(), request.getEmoji(), "긍정");

        reviewInfoService.saveReviewInfo(reviewInfo);

        return new CommentResponse("create comment success");
    }

    @GetMapping("/bookdetail/editForm/{reviewId}")
    public EditFormResponse commentEditForm(@PathVariable("reviewId") long reviewId) {
        ReviewInfo reviewInfo = reviewInfoService.findReviewInfo(reviewId);
        return new EditFormResponse(reviewInfo.getUserName(), reviewInfo.getContent(), reviewInfo.getEmoji());
    }

    @PutMapping("/bookdetail/edit/{reviewId}")
    public CommentResponse commentEdit(@PathVariable("reviewId") long reviewId,
                                       @RequestBody EditCommentRequest request) {
        String responseMessage = reviewInfoService.editReviewComment(reviewId, request.getContent(), request.getEmoji(), "Positive");
        return new CommentResponse(responseMessage);
    }

    @PostMapping("/bookdetail/delete/{reviewId}")
    public CommentResponse commentDelete(@PathVariable("reviewId") long reviewId,
                                         @RequestBody DeleteCommentRequest request) {
        String responseMessage = reviewInfoService.deleteReviewComment(reviewId, request.getPasswd());
        return new CommentResponse(responseMessage);
    }

    @GetMapping("/bookdetail/list/{isbn}")
    public Result commentList(@PathVariable("isbn") String isbn) {
        List<ReviewInfo> allReviewInfo = reviewInfoService.findAllReviewInfo(isbn);
        List<ReviewInfoDto> collect = allReviewInfo.stream()
                .map(m -> new ReviewInfoDto(m.getReviewId(), m.getUserName(), m.getContent(), m.getEmoji(), m.getResultAI()))
                .toList();

        return new Result(collect);
    }

    @GetMapping("/bookdetail/passwd")
    public CommentResponse editPasswdCheck(@RequestParam("reviewId") long reviewId,
                                           @RequestParam("passwd") String passwd) {
        ReviewInfo reviewInfo = reviewInfoService.findReviewInfo(reviewId);
        if(passwd.equals(reviewInfo.getReviewPw())) {
            return new CommentResponse("true");
        }
        return new CommentResponse("false");
    }
}
