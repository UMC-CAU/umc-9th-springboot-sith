package com.example.umc9th.domain.review.service.command;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.exception.MemberException;
import com.example.umc9th.domain.member.exception.code.MemberErrorCode;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc9th.domain.review.dto.res.ReviewResDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.entity.ReviewPhoto;
import com.example.umc9th.domain.review.repository.ReviewPhotoRepository;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.StoreErrorCode;
import com.example.umc9th.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService{

    private final ReviewRepository reviewRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public ReviewResDTO.ReviewCreateResDTO createReview(ReviewReqDTO.ReviewReqInfo dto) {
        Review review = ReviewConverter.toReview(dto);

        Member member = memberRepository.findById(dto.memberId())
                .orElseThrow(()->new MemberException(MemberErrorCode.NO_MEMBER));
        Store store = storeRepository.findById(dto.storeId())
                .orElseThrow(()->new StoreException(StoreErrorCode.NO_STORE));

        review.setMember(member);
        review.setStore(store);

        if(!dto.photoUrls().isEmpty()){

            List<ReviewPhoto> reviewPhotos = dto.photoUrls().stream().map(
                    url->ReviewPhoto.builder().photoUrl(url).review(review).build()
            ).collect(Collectors.toList());

            reviewPhotoRepository.saveAll(reviewPhotos);
        }

        reviewRepository.save(review);

        return ReviewConverter.toCreateResDTO(review);
    }
}