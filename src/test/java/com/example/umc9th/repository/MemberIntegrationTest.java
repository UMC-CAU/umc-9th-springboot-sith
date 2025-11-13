package com.example.umc9th.repository;

import com.example.umc9th.domain.member.entity.Member;
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
public class MemberIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager em;

    Long targetMemberId;
    @BeforeEach
    void setUp(){
        Member member = TestDataFactory.createMember(0);
        em.persist(member);
        targetMemberId = member.getId();
    }

    @Test
    @DisplayName("마이 페이지 보기")
    @Transactional
    void test()throws Exception{

        mockMvc.perform(get("/api/my-page").param("memberId", String.valueOf(targetMemberId))).andDo(print());
    }
}
