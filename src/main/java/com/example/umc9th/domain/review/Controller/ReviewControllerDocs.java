package com.example.umc9th.domain.review.Controller;

import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.global.annotation.ValidPageNumber;
import com.example.umc9th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewControllerDocs {
    @Operation(
            summary = "가게의 리뷰 목록 조회 api",
            description = "특정 가게의 리뷰를 모두 조회합니다. 페이지네이션으로 제공합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    ApiResponse<ReviewResDTO.ReviewPreViewListDTO> getReviews(
            @RequestParam String storeName, @RequestParam @ValidPageNumber Integer page
    );

    @Operation(
            summary = "내가 작성한 리뷰 목록 조회 api",
            description = "내가 작성한 리뷰를 모두 조회합니다. type = store일 때 query로 입력된 가게의 리뷰를 조회하고 " +
                    "type = rating일 때 query로 입력된 점수 별로(ex.4점대) 리뷰를 조회합니다. " +
                    "페이지네이션으로 제공합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패")
    })
    ApiResponse<ReviewResDTO.myReviewListDTO> findMyReviews(
            @RequestParam Long id, @RequestParam String query,
            @RequestParam String type, @ValidPageNumber @RequestParam Integer page);
}
