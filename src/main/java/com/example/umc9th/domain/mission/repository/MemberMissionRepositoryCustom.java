package com.example.umc9th.domain.mission.repository;


import com.example.umc9th.domain.mission.dto.SelectedMissionInfo;
import com.example.umc9th.domain.mission.dto.UnselectedMissionInfo;

import java.util.List;


public interface MemberMissionRepositoryCustom {
    List<SelectedMissionInfo> findSelectedMissionsWithCursor(Long memberId, String cursor, Boolean isCompleted, Integer pageSize);

    Long findCompletedMissionCountByDistrict(Long memberId,String district);

    List<UnselectedMissionInfo> findUnselectedMissionsByDistrictWithCursor(Long memberId, String cursor, String district, Integer pageSize);
}
