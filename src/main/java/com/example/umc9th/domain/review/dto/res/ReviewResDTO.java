package com.example.umc9th.domain.review.dto.res;

import com.example.umc9th.domain.review.dto.ReviewInfo;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    @Builder
    public record myReviewListDTO(
            List<ReviewInfo> reviews,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
            ){}

    @Builder
    public record Exception(String exception){}

    @Builder
    public record ReviewCreateResDTO(Long reviewId, LocalDateTime createdAt){}

    @Builder
    public record ReviewPreViewListDTO(
            List<ReviewPreViewDTO> reviewList,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ){}

    @Builder
    public record ReviewPreViewDTO(
            String ownerNickname,
            Double score,
            String content,
            LocalDate createdAt
    ){}
}
