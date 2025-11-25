package com.example.umc9th.domain.mission.service.command;

import com.example.umc9th.domain.mission.dto.SelectedMissionInfo;
import com.example.umc9th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc9th.domain.mission.dto.res.MissionResDTO;

public interface MissionCommandService {
    MissionResDTO.MissionRes createMission(MissionReqDTO.MissionReq dto);

    MissionResDTO.MemberMissionRes createMemberMission(MissionReqDTO.MemberMissionReq dto);

    SelectedMissionInfo completeMission(Long missionId);
}
