package com.example.umc9th.domain.mission.dto.res;

import com.example.umc9th.domain.mission.dto.SelectedMissionInfo;
import com.example.umc9th.domain.mission.dto.UnselectedMissionInfo;
import lombok.Builder;

import java.util.List;

public class MemberMissionResDTO {
    @Builder
    public record SelectedMissionList(
            List<SelectedMissionInfo> missions,
            Integer lastPoint, Long lastMemberMissionId, Boolean hasNext){}
    @Builder
    public record UnSelectedMissionList(
            List<UnselectedMissionInfo> missions,
            Integer lastPoint, Long lastMissionId, Boolean hasNext
    ){}
}
