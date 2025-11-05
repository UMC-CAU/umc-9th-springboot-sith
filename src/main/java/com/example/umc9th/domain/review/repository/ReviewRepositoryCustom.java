package com.example.umc9th.domain.review.repository;


import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ReviewRepositoryCustom {

    @Transactional
    List<ReviewInfo> findMyReviews(Long memberId, Predicate predicate);
}
