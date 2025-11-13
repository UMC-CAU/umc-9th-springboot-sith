package com.example.umc9th.repository;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.mapping.MemberMission;
import com.example.umc9th.domain.review.entity.Review;
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

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Import(TestConfig.class)
@AutoConfigureMockMvc
public class MemberMissionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager em;

    Long targetMemberId;
    String targetDistrict = "동작구";

    @BeforeEach
    void setUp(){

        Member member = TestDataFactory.createMember(0);
        em.persist(member);
        targetMemberId = member.getId();

        Member member1 = TestDataFactory.createMember(1);
        em.persist(member1);

        District district = TestDataFactory.createDistrict("동작구");
        em.persist(district);

        Store store = TestDataFactory.createStore(district,1);
        em.persist(store);

        Review review1 = TestDataFactory.createReview(member,store,0);
        em.persist(review1);

        for(int i=0;i<5;i++){
            Mission mission = TestDataFactory.createMission(store,i);
            em.persist(mission); //mission point 0,10,20,30,40
            MemberMission memberMission = TestDataFactory.createMemberMission(member,mission,true);
            em.persist(memberMission);
        }
        for(int i=5;i<8;i++){
            Mission mission = TestDataFactory.createMission(store,i);
            em.persist(mission); //mission point 50,60,70
            MemberMission memberMission = TestDataFactory.createMemberMission(member,mission,false);
            em.persist(memberMission);
        }

        for(int i=8;i<11;i++){
            Mission mission = TestDataFactory.createMission(store,i);
            em.persist(mission); //mission point 80,90,100
            MemberMission memberMission = TestDataFactory.createMemberMission(member1,mission,true);
            em.persist(memberMission);
        }
        Mission mission = TestDataFactory.createMission(store,11); //mission point 110
        em.persist(mission);

    }

    @Test
    @DisplayName("현재 진행중, 진행완료한 미션들")
    @Transactional
    void test() throws Exception {
        Boolean isCompleted = true;
        Integer pageSize = 3;

        mockMvc.perform(get("/api/my-missions")
                .param("memberId", String.valueOf(1))
                .param("isCompleted", String.valueOf(isCompleted))
                .param("pageSize", String.valueOf(pageSize))).andDo(print());

    }

    @Test
    @DisplayName("선택지역에서 완료한 미션 개수")
    @Transactional
    void test1() throws Exception{
        mockMvc.perform(get("/api/missions/completed/count")
                .param("memberId",String.valueOf(1))
                .param("district",targetDistrict)).andDo(print());
    }

    @Test
    @DisplayName("선택지역에서 도전할 수 있는 미션 개수")
    @Transactional
    void test2() throws Exception {

        Integer pageSize = 3;

        mockMvc.perform(get("/api/missions/unselected")
                .param("memberId", String.valueOf(1))
                .param("district", String.valueOf(targetDistrict))
                .param("pageSize", String.valueOf(pageSize))).andDo(print());

    }

}
