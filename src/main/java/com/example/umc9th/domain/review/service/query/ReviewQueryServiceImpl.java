package com.example.umc9th.domain.review.service.query;

import com.example.umc9th.domain.member.exception.code.MemberErrorCode;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.exception.ReviewException;
import com.example.umc9th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entity.QStore;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.StoreErrorCode;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewQueryServiceImpl implements ReviewQueryService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Override
    public ReviewResDTO.myReviewListDTO findMyReviews(Long memberId, String query, String type, Integer page){

        memberRepository.findById(memberId).orElseThrow(()->new ReviewException(MemberErrorCode.NO_MEMBER));

        QStore s = QStore.store;
        QReview r = QReview.review;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(!type.equals("store")&&!type.equals("rating")) throw new ReviewException(ReviewErrorCode.WRONG_TYPE);

        if(type.equals("store")){
            booleanBuilder.and(s.name.eq(query));
        }
        if(type.equals("rating")){
            booleanBuilder.and(r.rating.goe(Double.parseDouble(query))).and(r.rating.lt(Double.parseDouble(query)+1));
        }

        PageRequest pageRequest = PageRequest.of(page,10);
        Page<ReviewInfo> result = reviewRepository.findMyReviews(memberId,booleanBuilder,pageRequest);

        if(page >= result.getTotalPages()) throw new GeneralException(GeneralErrorCode.INVALID_PAGE_NUMBER);

        return ReviewConverter.toMyReviewListDTO(result);
    }

    @Override
    public ReviewResDTO.ReviewPreViewListDTO findReviews(String storeName, Integer page){

        Store store = storeRepository.findByName(storeName)
                .orElseThrow(()->new StoreException(StoreErrorCode.NO_STORE));
        PageRequest pageRequest = PageRequest.of(page,10);
        Page<Review> result = reviewRepository.findAllByStore(store,pageRequest);

        if(page >= result.getTotalPages()) throw new GeneralException(GeneralErrorCode.INVALID_PAGE_NUMBER);

        return ReviewConverter.toReviewPreviewListDTO(result);
    }
}