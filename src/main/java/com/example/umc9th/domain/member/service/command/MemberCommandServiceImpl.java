package com.example.umc9th.domain.member.service.command;

import com.example.umc9th.domain.member.converter.MemberConverter;
import com.example.umc9th.domain.member.dto.req.MemberReqDTO;
import com.example.umc9th.domain.member.dto.res.MemberResDTO;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.entity.mapping.MemberFood;
import com.example.umc9th.domain.member.repository.FoodRepository;
import com.example.umc9th.domain.member.repository.MemberFoodRepository;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.global.auth.enums.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService{

    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MemberResDTO.JoinDTO signUp(MemberReqDTO.JoinDTO dto){
        String salt = passwordEncoder.encode(dto.password());

        Member member = MemberConverter.toMember(dto,salt, Role.ROLE_USER);

        if(!dto.preferFoods().isEmpty()){
            List<MemberFood> memberFoods = dto.preferFoods().stream()
                    .map(id->MemberFood.builder()
                            .member(member)
                            .food(foodRepository.findById(id).get()).build())
                    .collect(Collectors.toList());
            memberFoodRepository.saveAll(memberFoods);
        }
        memberRepository.save(member);
        return MemberConverter.toJoinDTO(member);
    }
}
