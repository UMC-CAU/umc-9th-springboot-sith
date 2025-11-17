package com.example.umc9th.domain.mission.Controller;

import com.example.umc9th.domain.mission.Service.query.MemberMissionQueryService;
import com.example.umc9th.domain.mission.dto.res.MemberMissionResDTO;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MemberMissionController {

    private final MemberMissionQueryService memberMissionQueryService;

    @GetMapping("/api/my-missions")
    public ApiResponse<MemberMissionResDTO.SelectedMissionList> getSelectedMissions(
            @RequestParam Long memberId,
            @RequestParam(required = false) Integer lastPoint,
            @RequestParam(required = false) Long lastMemberMissionId,
            @RequestParam Boolean isCompleted,
            @RequestParam Integer pageSize){

        MemberMissionResDTO.SelectedMissionList result = memberMissionQueryService.findSelectedMissionsWithPaging(
                memberId, lastPoint, lastMemberMissionId, isCompleted, pageSize
        );
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, result);
    }

    @GetMapping("/api/missions/completed/count")
    public ApiResponse<Long> getCompletedMissionCount(
            @RequestParam Long memberId,
            @RequestParam String district)
    {
        Long result = memberMissionQueryService.findCompletedMissionsCountByDistrict(memberId,district);
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code,result);
    }

    @GetMapping("/api/missions/unselected")
    public ApiResponse<MemberMissionResDTO.UnSelectedMissionList> getUnselectedMissions(
            @RequestParam Long memberId,
            @RequestParam String district,
            @RequestParam(required = false) Integer lastPoint,
            @RequestParam(required = false) Long missionId,
            @RequestParam Integer pageSize
    ){
        MemberMissionResDTO.UnSelectedMissionList result = memberMissionQueryService.findUnselectedMissionsByDistrictWithPaging(
                memberId,district,lastPoint,missionId,pageSize
        );
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code,result);
    }
}
