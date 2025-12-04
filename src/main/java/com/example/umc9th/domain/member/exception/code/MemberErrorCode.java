package com.example.umc9th.domain.member.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    NO_MEMBER(HttpStatus.NOT_FOUND,"MEMBER_404_1","해당 멤버가 존재하지 않습니다."),
    INVALID_POINT(HttpStatus.BAD_REQUEST,"MEMBER_400_1","유효하지 않은 포인트입니다."),
    INVALID(HttpStatus.UNAUTHORIZED,"MEMBER_401_1","아이디나 비밀번호가 일치하지 않습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
