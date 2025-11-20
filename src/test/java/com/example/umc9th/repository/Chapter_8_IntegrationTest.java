package com.example.umc9th.repository;

import com.example.umc9th.domain.member.entity.Food;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.enums.FoodName;
import com.example.umc9th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.repository.MemberMissionRepository;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc9th.domain.review.service.query.ReviewQueryService;
import com.example.umc9th.domain.store.dto.req.StoreReqDTO;
import com.example.umc9th.domain.store.entity.District;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
@AutoConfigureMockMvc
public class Chapter_8_IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EntityManager em;
    @Autowired
    private ReviewQueryService reviewQueryService;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MemberMissionRepository memberMissionRepository;

    private Member member;
    private District district;
    private Store store;
    private Food food;
    private Mission mission;

    @BeforeEach
    void setUp(){
        member = TestDataFactory.createMember(0);
        em.persist(member);
        district = TestDataFactory.createDistrict("동작구");
        em.persist(district);
        store = TestDataFactory.createStore(district,0);
        em.persist(store);
        food = TestDataFactory.createFood(FoodName.KOREAN);
        em.persist(food);
        mission = TestDataFactory.createMission(store,0);
        em.persist(mission);
    }

    @Test
    @DisplayName("가게 추가하기 통합테스트")
    @Transactional
    void test1() throws Exception{


        StoreReqDTO.StoreReq storeReq = new StoreReqDTO.StoreReq(
                "가게1", "상도1동 999",district.getId(),food.getId());
        mockMvc.perform(post("/api/store").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storeReq))).andDo(print());
        Long id = 3L;
        var result = storeRepository.findById(id).get();

        System.out.println(result.getBossNumber());
        System.out.println(result.getName());
        System.out.println(result.getFood().getName());
        System.out.println(result.getDistrict().getName());

    }

    @Test
    @DisplayName("리뷰 추가하기 통합 테스트")
    @Transactional
    void test2() throws Exception{

        List<String> photos = List.of("photo1","photo2");
        ReviewReqDTO.ReviewReqInfo reqInfo = new ReviewReqDTO
                .ReviewReqInfo(member.getId(),store.getId(),4.5,"아주 맛있습니다.",photos);

        mockMvc.perform(post("/api/review").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqInfo))).andDo(print());

        System.out.println(reviewQueryService.findMyReviews(member.getId(),"Store0","store"));

    }

    @Test
    @DisplayName("미션 추가하기 통합 테스트")
    @Transactional
    void test3() throws Exception {
        MissionReqDTO.MissionReq missionReq = new MissionReqDTO.MissionReq(
                store.getId(),"1000원 이상 식사를 하세요.",1000, LocalDateTime.now().plusDays(7L)
        );

        mockMvc.perform(post("/api/mission").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(missionReq))).andDo(print());

        Long id = 1L;
        var result = missionRepository.findById(id).get();

        System.out.println(result.getStore().getName());
        System.out.println(result.getDeadline());
        System.out.println(result.getDescription());
        System.out.println(result.getPoint());

    }

    @Test
    @DisplayName("멤버_미션 추가하기(미션 도전하기) 통합 테스트")
    @Transactional
    void test4() throws Exception{
        MissionReqDTO.MemberMissionReq memberMissionReq = new MissionReqDTO.MemberMissionReq(
                member.getId(),mission.getId()
        );

        mockMvc.perform(post("/api/member-mission").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberMissionReq))).andDo(print());

        Long id = 1L;
        var result = memberMissionRepository.findById(id).get();
        System.out.println(result.getMember().getName());
        System.out.println(result.getMission().getDescription());

    }
}
