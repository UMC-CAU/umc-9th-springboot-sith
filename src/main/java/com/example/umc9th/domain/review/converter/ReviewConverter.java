package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.entity.Review;

import java.util.List;

public class ReviewConverter {
    public static ReviewResDTO.ReviewList toReviewDTO(List<ReviewInfo> reviews){
        return ReviewResDTO.ReviewList.builder()
                .reviews(reviews)
                .build();
    }
    public static ReviewResDTO.Exception toExceptionDTO(String exception){
        return ReviewResDTO.Exception.builder().exception(exception).build();
    }
    public static ReviewResDTO.ReviewCreateResDTO toCreateResDTO(Review review){
        return ReviewResDTO.ReviewCreateResDTO.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }
    public static Review toReview(ReviewReqDTO.ReviewReqInfo dto){
        return Review.builder()
                .content(dto.content())
                .rating(dto.rating())
                .build();
    }
}