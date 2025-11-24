package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.mission.dto.req.MissionReqDTO;

import com.example.umc9th.domain.mission.service.command.MissionCommandService;
import com.example.umc9th.domain.mission.service.query.MissionQueryService;
import com.example.umc9th.domain.mission.dto.res.MissionResDTO;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class MissionController {

    private final MissionQueryService missionQueryService;
    private final MissionCommandService missionCommandService;

    @GetMapping("/my-missions")
    public ApiResponse<MissionResDTO.SelectedMissionList> getSelectedMissions(
            @RequestParam Long memberId,
            @RequestParam(required = false) Integer lastPoint,
            @RequestParam(required = false) Long lastMemberMissionId,
            @RequestParam Boolean isCompleted,
            @RequestParam Integer pageSize){

        MissionResDTO.SelectedMissionList result = missionQueryService.findSelectedMissionsWithPaging(
                memberId, lastPoint, lastMemberMissionId, isCompleted, pageSize
        );
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, result);
    }

    @GetMapping("/missions/completed/count")
    public ApiResponse<Long> getCompletedMissionCount(
            @RequestParam Long memberId,
            @RequestParam String district)
    {
        Long result = missionQueryService.findCompletedMissionsCountByDistrict(memberId,district);
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code,result);
    }

    @GetMapping("/missions/unselected")
    public ApiResponse<MissionResDTO.UnSelectedMissionList> getUnselectedMissions(
            @RequestParam Long memberId,
            @RequestParam String district,
            @RequestParam(required = false) Integer lastPoint,
            @RequestParam(required = false) Long missionId,
            @RequestParam Integer pageSize
    ){
        MissionResDTO.UnSelectedMissionList result = missionQueryService.findUnselectedMissionsByDistrictWithPaging(
                memberId,district,lastPoint,missionId,pageSize
        );
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code,result);
    }

    @PostMapping("/missions")
    public ApiResponse<MissionResDTO.MissionRes> createMission(@RequestBody @Valid MissionReqDTO.MissionReq dto){
        MissionResDTO.MissionRes result = missionCommandService.createMission(dto);
        GeneralSuccessCode code = GeneralSuccessCode.CREATED;
        return ApiResponse.onSuccess(code,result);
    }

    @PostMapping("/member-missions")
    public ApiResponse<MissionResDTO.MemberMissionRes> createMemberMission(
            @RequestBody @Valid MissionReqDTO.MemberMissionReq dto)
    {
        MissionResDTO.MemberMissionRes result = missionCommandService.createMemberMission(dto);
        GeneralSuccessCode code = GeneralSuccessCode.CREATED;
        return ApiResponse.onSuccess(code,result);
    }
}
