package com.example.umc9th.domain.review.repository;


import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReviewRepositoryCustom {
    @Transactional
    Page<ReviewInfo> findMyReviews(Long memberId, Predicate predicate, Pageable pageable);
}
