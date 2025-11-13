package com.example.umc9th.domain.mission.Service.query;

import com.example.umc9th.domain.mission.dto.SelectedMissionInfo;
import com.example.umc9th.domain.mission.dto.UnselectedMissionInfo;
import com.example.umc9th.domain.mission.dto.res.MemberMissionResDTO;
import com.example.umc9th.domain.mission.repository.MemberMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberMissionQueryServiceImpl implements MemberMissionQueryService{

    private final MemberMissionRepository memberMissionRepository;
    @Override
    public MemberMissionResDTO.SelectedMissionList findSelectedMissionsWithPaging(Long memberId,
                                                                                  Integer lastPoint,
                                                                                  Long lastMemberMissionId,
                                                                                  Boolean isCompleted, Integer pageSize){

        String cursor = null;
        if(lastPoint !=null && lastMemberMissionId != null){
            cursor = String.format("%010d%010d",lastPoint,lastMemberMissionId);
        }
        List<SelectedMissionInfo> missions = memberMissionRepository.findSelectedMissionsWithCursor(
                memberId,cursor,isCompleted,pageSize);

        Integer nextPoint = null;
        Long nextId = null;
        Boolean hasNext = false;
        if(missions.size() == pageSize){
            SelectedMissionInfo lastMission = missions.get(pageSize-1);
            nextPoint = lastMission.getPoint();
            nextId = lastMission.getMemberMissionId();
            hasNext = true;
        }
        return new MemberMissionResDTO.SelectedMissionList(missions,nextPoint,nextId,hasNext);
    }

    @Override
    public Long findCompletedMissionsCountByDistrict(Long memberId, String district){
        return memberMissionRepository.findCompletedMissionCountByDistrict(memberId,district);
    }

    @Override
    public MemberMissionResDTO.UnSelectedMissionList findUnselectedMissionsByDistrictWithPaging(Long memberId,
                                                                                         String district,
                                                                                         Integer lastPoint,
                                                                                         Long lastMissionId,
                                                                                         Integer pageSize){
        String cursor = null;
        if(lastPoint !=null && lastMissionId != null){
            cursor = String.format("%010d%010d",lastPoint,lastMissionId);
        }
        List<UnselectedMissionInfo> missions = memberMissionRepository.findUnselectedMissionsByDistrictWithCursor(memberId,
                cursor,district,pageSize);

        Integer nextPoint = null;
        Long nextId = null;
        Boolean hasNext = false;
        if(missions.size() == pageSize){
            UnselectedMissionInfo lastMission = missions.get(pageSize-1);
            nextPoint = lastMission.getPoint();
            nextId = lastMission.getMissionId();
            hasNext = true;
        }

        return new MemberMissionResDTO.UnSelectedMissionList(missions,nextPoint,nextId,hasNext);
    }
}
