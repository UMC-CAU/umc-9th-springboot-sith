package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.QReviewPhoto;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ReviewRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ReviewInfo> findMyReviews(Long memberId,Predicate predicate) {
        QReview r = QReview.review;
        QStore s = QStore.store;
        QReviewPhoto rp = QReviewPhoto.reviewPhoto;

        List<ReviewInfo> result = jpaQueryFactory
                .from(r).join(r.store,s).leftJoin(rp).on(r.id.eq(rp.review.id))
                .where(r.member.id.eq(memberId).and(predicate))
                .transform(
                        groupBy(r.id).list(Projections.constructor(ReviewInfo.class,
                                r.id,s.name,r.rating,r.content,
                                list(rp.photoUrl),
                                r.updatedAt
                        ))
                );
        return result;
    }
}