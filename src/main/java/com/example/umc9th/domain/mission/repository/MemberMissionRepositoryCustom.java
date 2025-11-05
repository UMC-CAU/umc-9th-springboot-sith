package com.example.umc9th.domain.mission.repository;


import com.example.umc9th.domain.mission.dto.SelectedMissionsInfo;
import com.example.umc9th.domain.mission.dto.UnselectedMissionInfo;

import java.util.List;


public interface MemberMissionRepositoryCustom {
    List<SelectedMissionsInfo> findSelectedMissionsWithCursor(Long memberId, String cursor, boolean isCompleted,int pageSize);

    Long findCompletedMissionCountByDistrict(Long memberId,String district);

    List<UnselectedMissionInfo> findUnselectedMissionsByDistrictWithCursor(Long memberId, String cursor, String district, int pageSize);
}
