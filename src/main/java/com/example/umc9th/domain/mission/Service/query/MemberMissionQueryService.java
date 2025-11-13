package com.example.umc9th.domain.mission.Service.query;

import com.example.umc9th.domain.mission.dto.res.MemberMissionResDTO;


public interface MemberMissionQueryService {
    MemberMissionResDTO.SelectedMissionList findSelectedMissionsWithPaging(Long memberId,
                                                                           Integer lastPoint,
                                                                           Long lastMemberMissionId,
                                                                           Boolean isCompleted, Integer pageSize);

    Long findCompletedMissionsCountByDistrict(Long memberId, String district);

    MemberMissionResDTO.UnSelectedMissionList findUnselectedMissionsByDistrictWithPaging(Long memberId,
                                                                                         String district,
                                                                                         Integer lastPoint,
                                                                                         Long lastMissionId,
                                                                                         Integer pageSize);
}
