package com.example.umc9th.repository;

import com.example.umc9th.domain.member.entity.Food;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.enums.FoodName;
import com.example.umc9th.domain.member.enums.Gender;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.mapping.MemberMission;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.entity.ReviewPhoto;
import com.example.umc9th.domain.review.entity.ReviewReply;
import com.example.umc9th.domain.store.entity.District;
import com.example.umc9th.domain.store.entity.Store;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestDataFactory {

    public static Member createMember(int index) {
        return Member.builder()
                .name("mem" + index)                        // nullable = false
                .gender(Gender.MAN)                           // nullable = false
                .address("Seoul City")                         // nullable = false
                .email("member" + index + "@test.com")         // nullable = false
                .point(0)                                      // nullable = false
                .phoneNumber("010-0000-" + String.format("%04d", index)) // nullable = true
                .nickname("user" + index)// nullable = true
                .birth(LocalDate.now())
                .build();
    }

    public static Mission createMission(Store store, int index) {
        return Mission.builder()
                .deadline(LocalDateTime.now().plusDays(7))
                .description("Mission " + index)
                .point(10*index)
                .store(store)
                .build();
    }
    public static MemberMission createMemberMission(Member member, Mission mission, boolean isCompleted) {
        return MemberMission.builder()
                .member(member)
                .mission(mission)
                .isCompleted(isCompleted)
                .build();
    }
    public static Review createReview(Member member, Store store, int index) {
        return Review.builder()
                .content("review content " + index)
                .rating(Double.valueOf((index % 5) + 0.5))
                .member(member)
                .store(store)
                .build();
    }
    public static Store createStore(District district, int index) {
        return Store.builder()
                .name("Store" + index)
                .bossNumber("OWN-" + String.format("%04d", index))
                .detailAddress("Seoul Street " + index)
                .district(district)
                .build();
    }
    public static District createDistrict(String name) {
        return District.builder()
                .name(name)
                .build();
    }
    public static ReviewReply createReviewReply(Review review, int index) {
        return ReviewReply.builder()
                .content("reply comment " + index)
                .review(review)
                .build();
    }
    public static ReviewPhoto createReviewPhoto(Review review, int index) {
        return ReviewPhoto.builder()
                .photoUrl("https://test.image/" + index)
                .review(review)
                .build();
    }
    public static Food createFood(FoodName name){
        return Food.builder()
                .name(name)
                .build();
    }
}
