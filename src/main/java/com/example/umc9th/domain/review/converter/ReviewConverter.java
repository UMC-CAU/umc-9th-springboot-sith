package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.entity.Review;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public class ReviewConverter {
    public static ReviewResDTO.myReviewListDTO toMyReviewListDTO(Page<ReviewInfo> result){
        return ReviewResDTO.myReviewListDTO.builder()
                .reviews(result.getContent())
                .listSize(result.getSize())
                .totalPage(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .isFirst(result.isFirst())
                .isLast(result.isLast())
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

    public static ReviewResDTO.ReviewPreViewListDTO toReviewPreviewListDTO(Page<Review> result){
        return ReviewResDTO.ReviewPreViewListDTO.builder()
                .reviewList(result.getContent().stream()
                        .map(ReviewConverter::toReviewPreviewDTO)
                        .toList())
                .listSize(result.getSize())
                .totalPage(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .isFirst(result.isFirst())
                .isLast(result.isLast())
                .build();
    }

    public static ReviewResDTO.ReviewPreViewDTO toReviewPreviewDTO(Review review){
        return ReviewResDTO.ReviewPreViewDTO.builder()
                .ownerNickname(review.getMember().getName())
                .score(review.getRating())
                .content(review.getContent())
                .createdAt(LocalDate.from(review.getCreatedAt()))
                .build();
    }
}