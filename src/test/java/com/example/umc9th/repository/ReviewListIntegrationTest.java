package com.example.umc9th.repository;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.review.Controller.ReviewController;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.entity.ReviewPhoto;
import com.example.umc9th.domain.review.entity.ReviewReply;
import com.example.umc9th.domain.review.service.query.ReviewQueryServiceImpl;
import com.example.umc9th.domain.store.entity.District;
import com.example.umc9th.domain.store.entity.Store;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, ReviewQueryServiceImpl.class, ReviewController.class})
@AutoConfigureMockMvc
public class ReviewListIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager em;

    private Long targetMemberId;

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

        ReviewReply reply = TestDataFactory.createReviewReply(review,0);
        em.persist(reply);
        for(int i=0;i<3;i++){
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
    @DisplayName("리뷰 조회 통합 테스트")
    @Transactional
    void test() throws Exception {

        mockMvc.perform(get("/api/my-reviews").param("id",String.valueOf(targetMemberId))
                .param("query","Store1")
                .param("type","store"))
                .andDo(print());

    }
}