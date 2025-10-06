package com.example.umc9th.domain.member.entity;


import com.example.umc9th.domain.member.enums.Gender;
import com.example.umc9th.domain.member.enums.Status;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 6)
    private String name;

    @Column(name = "address",nullable = false, length = 320)
    private String address;

    @Column(name = "gender",nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Gender gender = Gender.UNKNOWN;

    @Column(name = "phone_number", unique = true, length = 13)
    private String phoneNumber;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "profile_url", nullable = false)
    @Builder.Default
    private String profileUrl = "/images/default_profile.png";

    @Column(name = "point",nullable = false)
    @Builder.Default
    private int point = 0;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIVE;

    @Column(name = "inactive_date")
    private LocalDateTime inactiveDate;

}
