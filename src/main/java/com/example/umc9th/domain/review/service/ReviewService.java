package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewInfo> findMyReviews(Long memberId, String query, String type){
        QStore s = QStore.store;
        QReview r = QReview.review;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(type.equals("store")){
            booleanBuilder.and(s.name.eq(query));
        }
        if(type.equals("rating")){
            booleanBuilder.and(r.rating.goe(Double.parseDouble(query))).and(r.rating.lt(Double.parseDouble(query)+1));
        }

       List<ReviewInfo> myReviews = reviewRepository.findMyReviews(memberId,booleanBuilder);

        return myReviews;
    }
}