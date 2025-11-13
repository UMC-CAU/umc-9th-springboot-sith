package com.example.umc9th.domain.member.converter;

import com.example.umc9th.domain.member.dto.res.MemberResDTO;
import com.example.umc9th.domain.member.entity.Member;

public class MemberConverter {
    public static MemberResDTO.myPageInfo toMemberDTO(Member m){
        return MemberResDTO.myPageInfo.builder().nickname(m.getNickname()).email(m.getEmail()).point(m.getPoint())
                .profileUrl(m.getProfileUrl()).phoneNum(m.getPhoneNumber()).build();
    }
}