package com.example.umc9th.repository;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.review.Controller.ReviewController;
import com.example.umc9th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc9th.domain.review.service.command.ReviewCommandServiceImpl;
import com.example.umc9th.domain.store.entity.District;
import com.example.umc9th.domain.store.entity.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, ReviewCommandServiceImpl.class, ReviewController.class})
@AutoConfigureMockMvc
public class ReviewCreateIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("리뷰 생성 통합 테스트")
    @Transactional
    void test1() throws Exception{
        Member member = TestDataFactory.createMember(0);
        District district = TestDataFactory.createDistrict("동작구");
        Store store = TestDataFactory.createStore(district,0);
        ReviewReqDTO.ReviewReqInfo reqInfo = new ReviewReqDTO.ReviewReqInfo(member,store,4.5,"아주 맛있습니다.");
        mockMvc.perform(post("/api/review").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqInfo))).andDo(print());

    }
}
