package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.mission.dto.res.MissionResDTO;
import com.example.umc9th.global.annotation.ValidPageNumber;
import com.example.umc9th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;

public interface MissionControllerDocs {
    @Operation(
            summary = "특정 가게의 미션 목록 조회 api",
            description = "가게명을 입력 받고 해당 가게의 미션을 모두 조회합니다. 페이지네이션으로 제공합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "미션 데이터 목록과 페이지네이션 메타데이터가 성공적으로 반환됨"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                    description = "실패 코드와 메시지가 반환됨",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    ApiResponse<MissionResDTO.MissionPreviewListDTO> getMissions(
            @RequestParam String storeName, @RequestParam(defaultValue = "1") @ValidPageNumber Integer page);
}
