package com.example.umc9th.domain.member.converter;

import com.example.umc9th.domain.member.dto.req.MemberReqDTO;
import com.example.umc9th.domain.member.dto.res.MemberResDTO;
import com.example.umc9th.domain.member.entity.Member;

public class MemberConverter {
    public static MemberResDTO.myPageInfo toMemberDTO(Member m){
        return MemberResDTO.myPageInfo.builder()
                .nickname(m.getNickname())
                .email(m.getEmail())
                .point(m.getPoint())
                .profileUrl(m.getProfileUrl())
                .phoneNum(m.getPhoneNumber())
                .build();
    }

    public static MemberResDTO.JoinDTO toJoinDTO(Member member){
        return MemberResDTO.JoinDTO.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static Member toMember(MemberReqDTO.JoinDTO dto){
        return Member.builder()
                .name(dto.name())
                .address(dto.address())
                .birth(dto.birth())
                .build();
    }
}