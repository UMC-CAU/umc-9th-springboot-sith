package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.entity.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long>, ReviewRepositoryCustom {
    @Query("select rr from ReviewReply rr where rr.review.id = :reviewId")
    List<ReviewReply> findReviewRepliesByReviewId(@Param("reviewId") Long reviewId);
}
