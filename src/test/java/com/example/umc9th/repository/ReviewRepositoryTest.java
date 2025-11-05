package com.example.umc9th.repository;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.review.dto.ReviewInfo;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.entity.ReviewPhoto;
import com.example.umc9th.domain.review.entity.ReviewReply;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.review.service.ReviewService;
import com.example.umc9th.domain.store.entity.District;
import com.example.umc9th.domain.store.entity.Store;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import({TestConfig.class, ReviewService.class})
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private ReviewService reviewService;

    Long targetMemberId;
    Long targetReviewId;

    @BeforeEach
    void setup(){

        Member member = TestDataFactory.createMember(0);
        em.persist(member);
        targetMemberId = member.getId();

        Member member1 = TestDataFactory.createMember(1);
        em.persist(member1);

        District district = TestDataFactory.createDistrict("동작구");
        em.persist(district);

        Store store1 = TestDataFactory.createStore(district,1);
        em.persist(store1);
        Store store2 = TestDataFactory.createStore(district,2);
        em.persist(store2);

        Review review = TestDataFactory.createReview(member,store1,0); // rating 0.5
        em.persist(review);
        targetReviewId = review.getId();
        for(int i=0;i<3;i++){
            ReviewReply reply = TestDataFactory.createReviewReply(review,i);
            em.persist(reply);
            ReviewPhoto photo = TestDataFactory.createReviewPhoto(review,i);
            em.persist(photo);
        }
        for(int i=1;i<5;i++){
            Review review1 = TestDataFactory.createReview(member,store1,i); // rating 1.5 2.5 3.5 4.5
            em.persist(review1);
        }
        for(int i=0;i<3;i++){
            Review review2 = TestDataFactory.createReview(member1,store1,i);
            em.persist(review2);
            Review review3 = TestDataFactory.createReview(member,store2,i); // rating 0.5 1.5 2.5
            em.persist(review3);
        }

    }
    @Test
    @DisplayName("내가 작성한 리뷰 장소 별 보기")
    void test(){

        List<ReviewInfo> result = reviewService.findMyReviews(targetMemberId,"Store1","store");
        assertThat(result).hasSize(5);
        assertThat(result.get(0).getStoreName()).isEqualTo("Store1");
        assertThat(result.get(4).getStoreName()).isEqualTo("Store1");
    }
    @Test
    @DisplayName("내가 작성한 리뷰 별점 별 보기")
    void test1(){

        List<ReviewInfo> result = reviewService.findMyReviews(targetMemberId,"0","rating");
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPhotoUrls()).hasSize(3);
        assertThat(result.get(1).getPhotoUrls()).hasSize(0);
        assertThat(result.get(0).getStoreName()).isEqualTo("Store1");
        assertThat(result.get(1).getStoreName()).isEqualTo("Store2");
        assertThat(result.get(0).getRating()).isEqualTo(0.5);
        assertThat(result.get(1).getRating()).isEqualTo(0.5);

        List<ReviewReply> replies = reviewRepository.findReviewRepliesByReviewId(targetReviewId);
        assertThat(replies).hasSize(3);

    }

}
