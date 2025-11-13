package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.dto.ReviewReplyInfo;
import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.QReviewPhoto;
import com.example.umc9th.domain.review.entity.QReviewReply;
import com.example.umc9th.domain.review.exception.ReviewException;
import com.example.umc9th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final MemberRepository memberRepository;

    @Override
    public List<ReviewInfo> findMyReviews(Long memberId,Predicate predicate) {
        memberRepository.findById(memberId).orElseThrow(()->new ReviewException(ReviewErrorCode.NO_MEMBER));

        QReview r = QReview.review;
        QStore s = QStore.store;
        QReviewPhoto rp = QReviewPhoto.reviewPhoto;
        QReviewReply rr = QReviewReply.reviewReply;

        List<ReviewInfo> result = jpaQueryFactory
                .from(r).join(r.store,s).leftJoin(rp).on(r.id.eq(rp.review.id)).leftJoin(rr).on(r.id.eq(rr.review.id))
                .where(r.member.id.eq(memberId).and(predicate))
                .transform(
                        groupBy(r.id).list(Projections.constructor(ReviewInfo.class,
                                r.id,s.name,r.rating,r.content,
                                list(rp.photoUrl),
                                Projections.constructor(ReviewReplyInfo.class,rr.content,rr.updatedAt),
                                r.updatedAt
                        ))
                );
        return result;
    }
}