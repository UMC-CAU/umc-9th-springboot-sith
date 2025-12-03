package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.dto.ReviewReplyInfo;
import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.QReviewPhoto;
import com.example.umc9th.domain.review.entity.QReviewReply;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ReviewInfo> findMyReviews(Long memberId, Predicate predicate, Pageable pageable) {

        QReview r = QReview.review;
        QStore s = QStore.store;
        QReviewPhoto rp = QReviewPhoto.reviewPhoto;
        QReviewReply rr = QReviewReply.reviewReply;

        List<Long> reviewIds = jpaQueryFactory
                .select(r.id)
                .from(r).join(r.store,s)
                .where(r.member.id.eq(memberId).and(predicate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ReviewInfo> result = jpaQueryFactory
                .from(r).join(r.store,s).leftJoin(rp).on(r.id.eq(rp.review.id)).leftJoin(rr).on(r.id.eq(rr.review.id))
                .where(r.id.in(reviewIds))
                .transform(
                        groupBy(r.id).list(Projections.constructor(ReviewInfo.class,
                                r.id,s.name,r.rating,r.content,
                                list(rp.photoUrl),
                                Projections.constructor(ReviewReplyInfo.class,rr.content,rr.updatedAt),
                                r.updatedAt
                        ))
                );
        JPAQuery<Long> countQuery = jpaQueryFactory.select(r.count())
                .from(r).join(r.store,s)
                .where(r.member.id.eq(memberId).and(predicate));

        return PageableExecutionUtils.getPage(result,pageable,countQuery::fetchOne);
    }
}