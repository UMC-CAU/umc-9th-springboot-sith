package com.example.umc9th.domain.review.service.command;

import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService{

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewInfo createReview(ReviewReqDTO.ReviewReqInfo request) {
        Review review = Review.builder().member(request.member()).content(request.content())
                .store(request.store()).rating(request.rating()).build();
        reviewRepository.save(review);
        ReviewInfo result = ReviewConverter.toReviewInfo(review);
        return result;
    }
}