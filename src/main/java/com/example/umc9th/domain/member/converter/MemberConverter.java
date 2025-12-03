package com.example.umc9th.domain.member.converter;

import com.example.umc9th.domain.member.dto.req.MemberReqDTO;
import com.example.umc9th.domain.member.dto.res.MemberResDTO;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.global.auth.enums.Role;

public class MemberConverter {
    public static MemberResDTO.myPageInfo toMyPageInfo(Member m){
        return MemberResDTO.myPageInfo.builder()
                .name(m.getName())
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

    public static Member toMember(MemberReqDTO.JoinDTO dto, String password, Role role){
        return Member.builder()
                .name(dto.name())
                .email(dto.email())
                .password(password)
                .role(role)
                .address(dto.address())
                .birth(dto.birth())
                .gender(dto.gender())
                .build();
    }
}