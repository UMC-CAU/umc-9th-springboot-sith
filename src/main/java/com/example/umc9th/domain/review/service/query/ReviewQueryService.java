package com.example.umc9th.domain.review.service.query;

import com.example.umc9th.domain.review.dto.ReviewInfo;

import java.util.List;

public interface ReviewQueryService {
    List<ReviewInfo> findMyReviews(Long memberId, String query, String type);
}
