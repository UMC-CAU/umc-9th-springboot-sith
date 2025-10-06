package com.example.umc9th.domain.member.entity;

import com.example.umc9th.domain.member.enums.TermName;
import com.example.umc9th.domain.member.enums.TermType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name ="term")
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private TermName name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TermType type;
}
