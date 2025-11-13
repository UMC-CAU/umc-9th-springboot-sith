package com.example.umc9th.domain.review.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    NO_MEMBER(HttpStatus.NOT_FOUND,"REVIEW_MEMBER400_1","해당 멤버가 존재하지 않습니다."),
    WRONG_TYPE(HttpStatus.BAD_REQUEST,"REVIEW_TYPE400_1","잘못된 타입입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
