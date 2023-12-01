package com.hallym.cloud.cloudpotato.repository;

import com.hallym.cloud.cloudpotato.domain.ReviewInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewInfoRepository extends JpaRepository<ReviewInfo, Long> {
}
