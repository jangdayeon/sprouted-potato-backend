package com.hallym.cloud.cloudpotato.repository;

import com.hallym.cloud.cloudpotato.domain.ReviewInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ReviewInfoRepository extends JpaRepository<ReviewInfo, Long> {
    @Query("select distinct r.isbn from ReviewInfo r")
    List<String> findDistinctIsbn();

    long countByIsbn(String isbn);

    long countByIsbnAndEmoji(String isbn, String emoji);

    List<ReviewInfo> findAllByIsbnOrderByReviewDateDesc(String isbn);

    long countByIsbnAndResultAI(String isbn, String resultai);
}
