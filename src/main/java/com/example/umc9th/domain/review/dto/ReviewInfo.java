package com.example.umc9th.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewInfo {
    private Long reviewId;
    private String storeName;
    private double rating;
    private String content;
    private List<String> photoUrls;
    private ReviewReplyInfo reply;
    private LocalDateTime updatedAt;
}