package com.example.umc9th.domain.member.dto.res;

import lombok.Builder;

public class MemberResDTO {
    @Builder
    public record myPageInfo(String nickname,String profileUrl,String email,String phoneNum,int point){}
}