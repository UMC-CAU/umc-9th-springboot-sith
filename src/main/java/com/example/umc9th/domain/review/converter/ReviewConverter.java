package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.entity.Review;

import java.util.List;

public class ReviewConverter {
    public static ReviewResDTO.ReviewList toReviewDTO(List<ReviewInfo> reviews){
        return ReviewResDTO.ReviewList.builder().reviews(reviews).build();
    }

    public static ReviewInfo toReviewInfo(Review review){
        return ReviewInfo.builder().reviewId(review.getId()).content(review.getContent()).rating(review.getRating())
                .storeName(review.getStore().getName()).updatedAt(review.getUpdatedAt()).build();
    }
    public static ReviewResDTO.Exception toExceptionDTO(String exception){
        return ReviewResDTO.Exception.builder().exception(exception).build();
    }
}