package com.example.umc9th.domain.member.service.query;

import com.example.umc9th.domain.member.dto.res.MemberResDTO;

public interface MemberQueryService {
    MemberResDTO.myPageInfo findMyPage(Long memberId);
}
