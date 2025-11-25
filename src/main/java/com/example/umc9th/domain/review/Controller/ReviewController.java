package com.example.umc9th.domain.review.Controller;

import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.service.command.ReviewCommandService;
import com.example.umc9th.domain.review.service.query.ReviewQueryService;
import com.example.umc9th.global.annotation.ValidPageNumber;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Validated
public class ReviewController implements ReviewControllerDocs{

    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;

    @Override
    @GetMapping("/my-reviews")
    public ApiResponse<ReviewResDTO.myReviewListDTO> findMyReviews(
            @RequestParam Long id, @RequestParam String query,
            @RequestParam String type, @ValidPageNumber @RequestParam(defaultValue = "1") Integer page)
    {
        ReviewResDTO.myReviewListDTO result = reviewQueryService.findMyReviews(id, query, type, page-1);
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, result);
    }

    @PostMapping("/reviews")
    public ApiResponse<ReviewResDTO.ReviewCreateResDTO> createReview(@RequestBody @Valid ReviewReqDTO.ReviewReqInfo request){
        ReviewResDTO.ReviewCreateResDTO result = reviewCommandService.createReview(request);
        GeneralSuccessCode code = GeneralSuccessCode.CREATED;
        return ApiResponse.onSuccess(code,result);
    }

    @Override
    @GetMapping("/reviews")
    public ApiResponse<ReviewResDTO.ReviewPreViewListDTO> getReviews(
            @RequestParam String storeName, @RequestParam(defaultValue = "1") @ValidPageNumber Integer page
    ){
        ReviewResDTO.ReviewPreViewListDTO result = reviewQueryService.findReviews(storeName, page-1);
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code,result);
    }
}
