package com.example.umc9th.domain.store.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DistrictErrorCode implements BaseErrorCode {

    NO_DISTRICT(HttpStatus.NOT_FOUND,"DISTRICT_404_1","해당하는 지역이 존재하지않습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
