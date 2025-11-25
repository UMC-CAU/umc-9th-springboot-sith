package com.example.umc9th.domain.mission.service.query;

import com.example.umc9th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc9th.domain.mission.dto.res.MissionResDTO;


public interface MissionQueryService {
    MissionResDTO.SelectedMissionList findSelectedMissionsWithPaging(MissionReqDTO.MyMissionReqDTO request);

    Long findCompletedMissionsCountByDistrict(Long memberId, String district);

    MissionResDTO.UnSelectedMissionList findUnselectedMissionsByDistrictWithPaging(Long memberId,
                                                                                   String district,
                                                                                   Integer lastPoint,
                                                                                   Long lastMissionId,
                                                                                   Integer pageSize);

    MissionResDTO.MissionPreviewListDTO getMissions(String storeName,Integer page);
}
