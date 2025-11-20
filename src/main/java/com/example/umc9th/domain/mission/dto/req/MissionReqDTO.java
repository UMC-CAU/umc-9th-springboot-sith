package com.example.umc9th.domain.mission.dto.req;

import com.example.umc9th.global.annotation.ExistMember;
import com.example.umc9th.global.annotation.ExistMission;
import com.example.umc9th.global.annotation.ExistStore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

public class MissionReqDTO {
    @Builder
    public record MissionReq(
            @NotNull
            @ExistStore
            Long storeId,
            @Size(min = 3, max = 300)
            String description,
            @Min(value = 0,message = "점수는 0점 이상이어야 합니다.")
            int point,
            LocalDateTime deadline){}

    @Builder
    public record MemberMissionReq(
            @NotNull
            @ExistMember
            Long memberId,
            @NotNull
            @ExistMission
            Long missionId
    ){}
}
