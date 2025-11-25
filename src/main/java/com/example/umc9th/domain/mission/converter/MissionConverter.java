package com.example.umc9th.domain.mission.converter;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc9th.domain.mission.dto.res.MissionResDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

public class MissionConverter {
    public static Mission toMission(MissionReqDTO.MissionReq dto){
        return Mission.builder()
                .point(dto.point())
                .description(dto.description())
                .deadline(dto.deadline())
                .build();
    }
    public static MissionResDTO.MissionRes toMissionRes(Mission mission){
        return MissionResDTO.MissionRes.builder()
                .missionId(mission.getId())
                .createdAt(mission.getCreatedAt())
                .build();
    }
    public static MemberMission toMemberMission(Member member, Mission mission){
        return MemberMission.builder()
                .mission(mission)
                .member(member)
                .isCompleted(false)
                .build();
    }
    public static MissionResDTO.MemberMissionRes toMemberMissionRes(MemberMission memberMission){
        return MissionResDTO.MemberMissionRes.builder()
                .memberMissionId(memberMission.getId())
                .createdAt(memberMission.getCreatedAt())
                .build();
    }

    public static MissionResDTO.MissionPreviewListDTO toMissionPreviewList(Page<Mission> result){
        return MissionResDTO.MissionPreviewListDTO.builder()
                .missionList(result.getContent()
                        .stream()
                        .map(MissionConverter::toMissionPreview).toList())
                .isFirst(result.isFirst())
                .isLast(result.isLast())
                .listSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPage(result.getTotalPages())
                .build();
    }

    public static MissionResDTO.MissionPreviewDTO toMissionPreview(Mission mission){
        return MissionResDTO.MissionPreviewDTO.builder()
                .description(mission.getDescription())
                .point(mission.getPoint())
                .deadLine(mission.getDeadline().toLocalDate()).build();
    }
}
