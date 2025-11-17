package com.example.umc9th.domain.mission.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    NO_MEMBER(HttpStatus.NOT_FOUND,"MISSION_MEMBER400_1","해당 멤버가 존재하지 않습니다."),
    NO_DISTRICT(HttpStatus.NOT_FOUND,"MISSION_DISTRICT400_1","해당 지역이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
