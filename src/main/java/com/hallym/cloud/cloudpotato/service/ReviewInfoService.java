package com.hallym.cloud.cloudpotato.service;

import com.hallym.cloud.cloudpotato.domain.ReviewInfo;
import com.hallym.cloud.cloudpotato.repository.ReviewInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

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
    public String editReviewComment(long reviewId, String content, String emoji, String resultAI) {
        Optional<ReviewInfo> findReviewInfo = reviewInfoRepository.findById(reviewId);
        findReviewInfo.ifPresent(findReview -> findReview.change(content, emoji, resultAI));

        return "edit comment success";
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

    @Transactional(readOnly = true)
    public List<String> findByEmoji(String emoji){
        //리뷰작성한 책 모두 가져오기
        List<String> list1 = reviewInfoRepository.findDistinctIsbn();
        List<String[]> list2 = new ArrayList<>(list1.size());
        for(int i=0;i<list1.size();i++){
            Long cnt = reviewInfoRepository.countByIsbnAndEmoji(list1.get(i),emoji);
            list2.add(new String[]{list1.get(i), String.valueOf(cnt)});
        }

        // cnt를 큰 순서대로 정렬
        Collections.sort(list2, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                // cnt는 두 번째 인덱스에 위치하므로 두 번째 인덱스로 비교
                return Integer.compare(Integer.parseInt(o2[1]), Integer.parseInt(o1[1]));
            }
        });

        List<String> returnList = new ArrayList<>(list2.size());
        for(int i=0;i<list2.size();i++){
            returnList.add(list2.get(i)[0]);
        }

        return returnList;
    }

//    @Transactional(readOnly = true)
//    public List<String> findByResultAI(String resultAI){
//        //리뷰작성한 책 모두 가져오기
//        List<String> list1 = reviewInfoRepository.findDistinctIsbn();
//        List<String[]> list2 = new ArrayList<>(list1.size());
//        for(int i=0;i<list1.size();i++){
//            Long cnt = reviewInfoRepository.countByIsbnAndEmoji(list1.get(i),emoji);
//            list2.add(new String[]{list1.get(i), String.valueOf(cnt)});
//        }
//
//        // cnt를 큰 순서대로 정렬
//        Collections.sort(list2, new Comparator<String[]>() {
//            @Override
//            public int compare(String[] o1, String[] o2) {
//                // cnt는 두 번째 인덱스에 위치하므로 두 번째 인덱스로 비교
//                return Integer.compare(Integer.parseInt(o2[1]), Integer.parseInt(o1[1]));
//            }
//        });
//
//        List<String> returnList = new ArrayList<>(list2.size());
//        for(int i=0;i<list2.size();i++){
//            returnList.add(list2.get(i)[0]);
//        }
//
//        return returnList;
//    }

    @Transactional(readOnly = true)
    public List<String> findByEmojis(){
        //리뷰작성한 책 모두 가져오기
        List<String> list1 = reviewInfoRepository.findDistinctIsbn();
        List<String[]> list2 = new ArrayList<>(list1.size());
        for(int i=0;i<list1.size();i++){
            Long cnt = reviewInfoRepository.countByIsbnAndEmoji(list1.get(i),"\uD83D\uDE04");
            cnt += reviewInfoRepository.countByIsbnAndEmoji(list1.get(i),"\uD83E\uDD79");
            cnt += reviewInfoRepository.countByIsbnAndEmoji(list1.get(i),"\uD83D\uDE0D");
            list2.add(new String[]{list1.get(i), String.valueOf(cnt)});
        }

        // cnt를 큰 순서대로 정렬
        Collections.sort(list2, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                // cnt는 두 번째 인덱스에 위치하므로 두 번째 인덱스로 비교
                return Integer.compare(Integer.parseInt(o2[1]), Integer.parseInt(o1[1]));
            }
        });

        List<String> returnList = new ArrayList<>(list2.size());
        for(int i=0;i<list2.size();i++){ //순위 탑 18위까지
            returnList.add(list2.get(i)[0]);
        }

        return returnList;
    }

    @Transactional(readOnly = true)
    public List<String> findByReviews(){
        //리뷰작성한 책 모두 가져오기
        List<String> list1 = reviewInfoRepository.findDistinctIsbn();
        List<String[]> list2 = new ArrayList<>(list1.size());
        for(int i=0;i<list1.size();i++){
            Long cnt = reviewInfoRepository.countByIsbn(list1.get(i));
            list2.add(new String[]{list1.get(i), String.valueOf(cnt)});
        }

        // cnt를 큰 순서대로 정렬
        Collections.sort(list2, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                // cnt는 두 번째 인덱스에 위치하므로 두 번째 인덱스로 비교
                return Integer.compare(Integer.parseInt(o2[1]), Integer.parseInt(o1[1]));
            }
        });

        List<String> returnList = new ArrayList<>(list2.size());
        for(int i=0;i<list2.size();i++){
            returnList.add(list2.get(i)[0]);
        }

        return returnList;
    }

}
