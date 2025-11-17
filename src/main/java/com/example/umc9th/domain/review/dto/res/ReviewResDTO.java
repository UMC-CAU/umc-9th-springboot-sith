package com.example.umc9th.domain.review.dto.res;

import com.example.umc9th.domain.review.dto.ReviewInfo;
import lombok.Builder;

import java.util.List;

public class ReviewResDTO {

    @Builder
    public record ReviewList(List<ReviewInfo> reviews){}

    @Builder
    public record Exception(String exception){}
}
