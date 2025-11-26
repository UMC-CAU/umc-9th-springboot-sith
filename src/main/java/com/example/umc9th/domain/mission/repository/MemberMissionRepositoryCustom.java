package com.example.umc9th.domain.mission.repository;


import com.example.umc9th.domain.mission.dto.SelectedMissionInfo;
import com.example.umc9th.domain.mission.dto.UnselectedMissionInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;


public interface MemberMissionRepositoryCustom {
    Slice<SelectedMissionInfo> findSelectedMissionsWithCursor(Long memberId, String cursor, boolean isCompleted, Pageable pageable);

    Long findCompletedMissionCountByDistrict(Long memberId,String district);

    List<UnselectedMissionInfo> findUnselectedMissionsByDistrictWithCursor(Long memberId, String cursor, String district, Integer pageSize);
}
