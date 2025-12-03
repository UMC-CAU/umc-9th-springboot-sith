package com.example.umc9th.domain.mission.service.command;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.exception.MemberException;
import com.example.umc9th.domain.member.exception.code.MemberErrorCode;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.mission.converter.MissionConverter;
import com.example.umc9th.domain.mission.dto.SelectedMissionInfo;
import com.example.umc9th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc9th.domain.mission.dto.res.MissionResDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.mapping.MemberMission;
import com.example.umc9th.domain.mission.exception.MissionException;
import com.example.umc9th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc9th.domain.mission.repository.MemberMissionRepository;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.StoreErrorCode;
import com.example.umc9th.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MissionCommandServiceImpl implements MissionCommandService {

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;

    @Override
    public MissionResDTO.MissionRes createMission(MissionReqDTO.MissionReq dto){
        Mission mission = MissionConverter.toMission(dto);
        Store store = storeRepository.findById(dto.storeId())
                .orElseThrow(()->new StoreException(StoreErrorCode.NO_STORE));

        mission.setStore(store);
        missionRepository.save(mission);

        return MissionConverter.toMissionRes(mission);
    }

    @Override
    public MissionResDTO.MemberMissionRes createMemberMission(MissionReqDTO.MemberMissionReq dto){
        Member member = memberRepository.findById(dto.memberId())
                .orElseThrow(()->new MemberException(MemberErrorCode.NO_MEMBER));
        Mission mission = missionRepository.findById(dto.missionId())
                .orElseThrow(()->new MissionException(MissionErrorCode.NO_MISSION));

        MemberMission memberMission = MissionConverter.toMemberMission(member,mission);

        memberMissionRepository.save(memberMission);

        return MissionConverter.toMemberMissionRes(memberMission);
    }

    @Override
    @Transactional
    public SelectedMissionInfo completeMission(Long missionId) {
        MemberMission memberMission = memberMissionRepository.findById(missionId)
                .orElseThrow(()->new MissionException(MissionErrorCode.NO_MEMBER_MISSION));
        if(memberMission.isCompleted()) throw new MissionException(MissionErrorCode.ALREADY_COMPLETED);

        Member member = memberMission.getMember();
        Mission mission = memberMission.getMission();
        memberMission.setCompleted(true);

        member.addPoint(mission.getPoint());

        return MissionConverter.toSelectedMission(mission,memberMission);
    }

}
