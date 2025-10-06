package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entity.mapping.MemberTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTermRepository extends JpaRepository<MemberTerm,Long> {
}
