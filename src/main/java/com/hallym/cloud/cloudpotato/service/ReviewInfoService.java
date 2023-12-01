package com.hallym.cloud.cloudpotato.service;

import com.hallym.cloud.cloudpotato.repository.ReviewInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewInfoService {
    private final ReviewInfoRepository reviewInfoRepository;
}
