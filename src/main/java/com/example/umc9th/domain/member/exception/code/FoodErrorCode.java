package com.example.umc9th.domain.member.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FoodErrorCode implements BaseErrorCode {

    NO_FOOD(HttpStatus.NOT_FOUND,"FOOD_404_1","해당하는 음식이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
