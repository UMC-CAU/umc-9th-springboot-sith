package com.example.umc9th.domain.review.dto.req;

import com.example.umc9th.global.annotation.ExistMember;
import com.example.umc9th.global.annotation.ExistStore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

public class ReviewReqDTO {
    @Builder
    public record ReviewReqInfo(
            @NotNull
            @ExistMember
            Long memberId,
            @NotNull
            @ExistStore
            Long storeId,
            @Min(value = 0, message = "점수는 0점 이상이어야 합니다.")
            @Max(value = 5, message = "점수는 5점 이하이어야 합니다.")
            Double rating,
            @NotBlank
            String content,
            List<String> photoUrls){}
}
