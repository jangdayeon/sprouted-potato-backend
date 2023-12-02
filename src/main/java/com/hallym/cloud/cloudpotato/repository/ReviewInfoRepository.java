package com.hallym.cloud.cloudpotato.repository;

import com.hallym.cloud.cloudpotato.domain.ReviewInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewInfoRepository extends JpaRepository<ReviewInfo, Long> {

    long countByIsbnAndEmoji(String isbn, String emoji);

    List<ReviewInfo> findAllByIsbn(String isbn);
}
