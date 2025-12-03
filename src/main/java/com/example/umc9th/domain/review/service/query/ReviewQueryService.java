package com.example.umc9th.domain.review.service.query;

import com.example.umc9th.domain.review.dto.res.ReviewResDTO;


public interface ReviewQueryService {
    ReviewResDTO.myReviewListDTO findMyReviews(Long memberId, String query, String type, Integer page);

    ReviewResDTO.ReviewPreViewListDTO findReviews(String storeName, Integer page);
}
