package com.example.umc9th.domain.mission.service.query;

import com.example.umc9th.domain.mission.dto.res.MissionResDTO;


public interface MissionQueryService {
    MissionResDTO.SelectedMissionList findSelectedMissionsWithPaging(Long memberId,
                                                                     Integer lastPoint,
                                                                     Long lastMemberMissionId,
                                                                     Boolean isCompleted, Integer pageSize);

    Long findCompletedMissionsCountByDistrict(Long memberId, String district);

    MissionResDTO.UnSelectedMissionList findUnselectedMissionsByDistrictWithPaging(Long memberId,
                                                                                   String district,
                                                                                   Integer lastPoint,
                                                                                   Long lastMissionId,
                                                                                   Integer pageSize);

    MissionResDTO.MissionPreviewListDTO getMissions(String storeName,Integer page);
}
