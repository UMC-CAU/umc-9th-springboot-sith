package com.example.umc9th.domain.mission.dto.res;

import com.example.umc9th.domain.mission.dto.SelectedMissionInfo;
import com.example.umc9th.domain.mission.dto.UnselectedMissionInfo;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {
    @Builder
    public record SelectedMissionList(
            List<SelectedMissionInfo> missions,
            Integer lastPoint,
            Long lastMemberMissionId,
            boolean hasNext){}

    @Builder
    public record UnSelectedMissionList(
            List<UnselectedMissionInfo> missions,
            Integer lastPoint, Long lastMissionId, Boolean hasNext
    ){}

    @Builder
    public record MissionRes(Long missionId, LocalDateTime createdAt){}

    @Builder
    public record MemberMissionRes(Long memberMissionId, LocalDateTime createdAt){}

    @Builder
    public record MissionPreviewListDTO(
            List<MissionPreviewDTO> missionList,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ){}

    @Builder
    public record MissionPreviewDTO(
            String description,
            Integer point,
            LocalDate deadLine
    ){}
}
