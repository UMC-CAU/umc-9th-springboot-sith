package com.example.umc9th.domain.review.dto.req;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.store.entity.Store;
import lombok.Builder;

public class ReviewReqDTO {
    @Builder
    public record ReviewReqInfo(Member member, Store store, Double rating, String content){}
}
