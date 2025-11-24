package com.example.umc9th.domain.store.controller;

import com.example.umc9th.domain.store.dto.req.StoreReqDTO;
import com.example.umc9th.domain.store.dto.res.StoreResDTO;
import com.example.umc9th.domain.store.service.command.StoreCommandService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreCommandService storeCommandService;

    @PostMapping("/api/stores")
    ApiResponse<StoreResDTO.StoreRes> createStore(@RequestBody @Valid StoreReqDTO.StoreReq dto){
        StoreResDTO.StoreRes result = storeCommandService.createStore(dto);
        GeneralSuccessCode code = GeneralSuccessCode.CREATED;
        return ApiResponse.onSuccess(code,result);
    }
}
