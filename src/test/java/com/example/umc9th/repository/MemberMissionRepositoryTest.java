package com.example.umc9th.repository;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.mission.dto.SelectedMissionsInfo;
import com.example.umc9th.domain.mission.dto.UnselectedMissionInfo;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.mapping.MemberMission;
import com.example.umc9th.domain.mission.repository.MemberMissionRepository;
import com.example.umc9th.domain.review.entity.Review;
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
@Import(TestConfig.class)
public class MemberMissionRepositoryTest {

    @Autowired
    private MemberMissionRepository memberMissionRepository;
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

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("선택 지역에서 완료한 미션 개수")
    void test1(){

        Long result = memberMissionRepository.findCompletedMissionCountByDistrict(targetMemberId,targetDistrict);
        assertThat(result).isEqualTo(5);

    }

    @Test
    @DisplayName("선택 지역에서 도전할 수 있는 미션들")
    void test2(){
        int pageSize = 3;
        List<UnselectedMissionInfo> page1 = memberMissionRepository.findUnselectedMissionsByDistrictWithCursor(
                targetMemberId,null,targetDistrict,pageSize);
        assertThat(page1).hasSize(3);
        assertThat(page1.get(0).getPoint()).isEqualTo(110);
        assertThat(page1.get(2).getPoint()).isEqualTo(90);

        UnselectedMissionInfo lastMission = page1.get(pageSize-1);
        String lastPoint = String.format("%010d",lastMission.getPoint());
        String lastId = String.format("%010d",lastMission.getMissionId());
        String cursor = lastPoint + lastId;

        System.out.println(cursor);

        List<UnselectedMissionInfo> page2 = memberMissionRepository.findUnselectedMissionsByDistrictWithCursor(
                targetMemberId,cursor,targetDistrict,pageSize);
        assertThat(page2).hasSize(1);
        assertThat(page2.get(0).getPoint()).isEqualTo(80);
    }

    @Test
    @DisplayName("현재 진행중, 진행완료한 미션들")
    void test3(){
        int pageSize = 3;
        List<SelectedMissionsInfo> page1 = memberMissionRepository.findSelectedMissionsWithCursor(
                targetMemberId,null,true,pageSize);
        assertThat(page1).hasSize(3);
        assertThat(page1.get(0).getPoint()).isEqualTo(40);
        assertThat(page1.get(2).getPoint()).isEqualTo(20);

        SelectedMissionsInfo lastMission = page1.get(pageSize-1);
        String lastPoint = String.format("%010d",lastMission.getPoint());
        String lastId = String.format("%010d",lastMission.getMemberMissionId());
        String cursor = lastPoint + lastId;

        List<SelectedMissionsInfo> page2 = memberMissionRepository.findSelectedMissionsWithCursor(
                targetMemberId,cursor,true,pageSize);
        assertThat(page2).hasSize(2);
        assertThat(page2.get(0).getPoint()).isEqualTo(10);
        assertThat(page2.get(1).getPoint()).isEqualTo(0);

    }

}
