package com.example.umc9th.domain.member.controller;

import com.example.umc9th.domain.member.dto.req.MemberReqDTO;
import com.example.umc9th.domain.member.dto.res.MemberResDTO;
import com.example.umc9th.domain.member.service.command.MemberCommandService;
import com.example.umc9th.domain.member.service.query.MemberQueryService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @GetMapping("/api/my-page")
    public ApiResponse<MemberResDTO.myPageInfo> getMyPage(@RequestParam Long memberId){
        MemberResDTO.myPageInfo result = memberQueryService.findMyPage(memberId);
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code,result);
    }

    @PostMapping("/sign-up")
    public ApiResponse<MemberResDTO.JoinDTO> signUp(@RequestBody @Valid MemberReqDTO.JoinDTO dto){
        MemberResDTO.JoinDTO result = memberCommandService.signUp(dto);
        GeneralSuccessCode code = GeneralSuccessCode.CREATED;
        return ApiResponse.onSuccess(code,result);
    }
}
