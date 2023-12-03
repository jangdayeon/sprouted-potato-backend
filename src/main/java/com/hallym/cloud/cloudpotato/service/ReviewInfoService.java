package com.hallym.cloud.cloudpotato.service;

import com.hallym.cloud.cloudpotato.domain.ReviewInfo;
import com.hallym.cloud.cloudpotato.repository.ReviewInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewInfoService {
    private final ReviewInfoRepository reviewInfoRepository;

    // reviewInfoEntity 생성
    @Transactional
    public void saveReviewInfo(ReviewInfo reviewInfo) {
        reviewInfoRepository.save(reviewInfo);
    }

    // 해당되는 reviewId를 가진 reviewInfoEntity 찾기
    public ReviewInfo findReviewInfo(long reviewId) {
        Optional<ReviewInfo> findReviewInfo = reviewInfoRepository.findById(reviewId);

        return findReviewInfo.orElse(null);
    }

    // comment 수정
    @Transactional
    public String editReviewComment(long reviewId, String pw, String content, String emoji, String resultAI) {
        Optional<ReviewInfo> findReviewInfo = reviewInfoRepository.findById(reviewId);
        if(findReviewInfo.isPresent()) {
            ReviewInfo ri = findReviewInfo.get();
            if(ri.getReviewPw().equals(pw)) {
                ri.change(content, emoji, resultAI);
                return "edit comment success";
            }
        }

        return "edit comment failed";
    }

    // comment 삭제
    @Transactional
    public String deleteReviewComment(long reviewId, String pw) {
        Optional<ReviewInfo> findReviewInfo = reviewInfoRepository.findById(reviewId);
        if(findReviewInfo.isPresent()) {
            ReviewInfo ri = findReviewInfo.get();
            if(ri.getReviewPw().equals(pw)) {
                reviewInfoRepository.deleteById(reviewId);
                return "delete comment success";
            } else {
                return "passwd is wrong";
            }
        }

        return "delete comment failed";
    }

    // isbn에 따른 해당 이모지 개수 count
    public long countByIsbnEmoji(String isbn, String emoji) {
        return reviewInfoRepository.countByIsbnAndEmoji(isbn, emoji);
    }

    public List<ReviewInfo> findAllReviewInfo(String isbn) {
        return reviewInfoRepository.findAllByIsbnOrderByReviewDateDesc(isbn);
    }
}
