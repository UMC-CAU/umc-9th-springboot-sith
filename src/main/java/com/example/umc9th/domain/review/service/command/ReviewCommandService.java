package com.example.umc9th.domain.review.service.command;

import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;

public interface ReviewCommandService {
    ReviewInfo createReview(ReviewReqDTO.ReviewReqInfo request);
}
