package com.example.umc9th.domain.review.Controller;

import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.service.command.ReviewCommandService;
import com.example.umc9th.domain.review.service.query.ReviewQueryService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;

    @GetMapping("/api/my-reviews")
    public ApiResponse<ReviewResDTO.ReviewList> findReviews(
            @RequestParam Long id, @RequestParam String query, @RequestParam String type){
        List<ReviewInfo> result = reviewQueryService.findMyReviews(id,query,type);
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, ReviewConverter.toReviewDTO(result));
    }

    @PostMapping("/api/review")
    public ApiResponse<ReviewInfo> createReview(@RequestBody ReviewReqDTO.ReviewReqInfo request){
        ReviewInfo result = reviewCommandService.createReview(request);
        GeneralSuccessCode code = GeneralSuccessCode.CREATED;
        return ApiResponse.onSuccess(code,result);
    }
}
