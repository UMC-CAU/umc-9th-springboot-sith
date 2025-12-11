package com.example.umc9th.domain.member.dto.res;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResDTO {
    @Builder
    public record myPageInfo(String name, String profileUrl, String email, String phoneNum, int point){}

    @Builder
    public record JoinDTO(Long memberId, LocalDateTime createdAt){}

    @Builder
    public record LoginDTO(Long memberId, String accessToken){}
}