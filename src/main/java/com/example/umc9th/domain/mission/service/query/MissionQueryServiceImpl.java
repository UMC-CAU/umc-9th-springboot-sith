package com.example.umc9th.domain.mission.service.query;

import com.example.umc9th.domain.member.exception.code.MemberErrorCode;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.mission.converter.MissionConverter;
import com.example.umc9th.domain.mission.dto.SelectedMissionInfo;
import com.example.umc9th.domain.mission.dto.UnselectedMissionInfo;
import com.example.umc9th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc9th.domain.mission.dto.res.MissionResDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.exception.MissionException;
import com.example.umc9th.domain.mission.repository.MemberMissionRepository;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.StoreErrorCode;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionQueryServiceImpl implements MissionQueryService {

    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;

    @Override
    public MissionResDTO.SelectedMissionList findSelectedMissionsWithPaging(MissionReqDTO.MyMissionReqDTO request){

        Long memberId = request.memberId();
        Long lastMemberMissionId = request.lastMemberMissionId();
        Integer lastPoint = request.lastPoint();
        boolean isCompleted = request.isCompleted();

        memberRepository.findById(memberId).orElseThrow(()->new MissionException(MemberErrorCode.NO_MEMBER));

        String cursor = null;
        if(lastPoint !=null && lastMemberMissionId != null){
            cursor = String.format("%010d%010d",lastPoint,lastMemberMissionId);
        }
        PageRequest pageRequest = PageRequest.of(0,3);

        Slice<SelectedMissionInfo> missions = memberMissionRepository.findSelectedMissionsWithCursor(
                memberId,cursor,isCompleted,pageRequest);

        Long nextId = null;
        Integer nextPoint = null;

        if(missions.hasNext()){
            SelectedMissionInfo lastMission = missions.getContent().getLast();
            nextId = lastMission.getMemberMissionId();
            nextPoint = lastMission.getPoint();
        }
        return new MissionResDTO.SelectedMissionList(missions.getContent(),nextPoint,nextId,missions.hasNext());
    }

    @Override
    public Long findCompletedMissionsCountByDistrict(Long memberId, String district){
        memberRepository.findById(memberId).orElseThrow(()->new MissionException(MemberErrorCode.NO_MEMBER));
        return memberMissionRepository.findCompletedMissionCountByDistrict(memberId,district);
    }

    @Override
    public MissionResDTO.UnSelectedMissionList findUnselectedMissionsByDistrictWithPaging(Long memberId,
                                                                                          String district,
                                                                                          Integer lastPoint,
                                                                                          Long lastMissionId,
                                                                                          Integer pageSize){

        memberRepository.findById(memberId).orElseThrow(()->new MissionException(MemberErrorCode.NO_MEMBER));

        String cursor = null;
        if(lastPoint !=null && lastMissionId != null){
            cursor = String.format("%010d%010d",lastPoint,lastMissionId);
        }
        List<UnselectedMissionInfo> missions = memberMissionRepository.findUnselectedMissionsByDistrictWithCursor(memberId,
                cursor,district,pageSize);

        Integer nextPoint = null;
        Long nextId = null;
        Boolean hasNext = false;
        if(missions.size() == pageSize){
            UnselectedMissionInfo lastMission = missions.get(pageSize-1);
            nextPoint = lastMission.getPoint();
            nextId = lastMission.getMissionId();
            hasNext = true;
        }

        return new MissionResDTO.UnSelectedMissionList(missions,nextPoint,nextId,hasNext);
    }

    @Override
    public MissionResDTO.MissionPreviewListDTO getMissions(String storeName,Integer page){
        Store store = storeRepository.findByName(storeName)
                .orElseThrow(()->new StoreException(StoreErrorCode.NO_STORE));
        PageRequest pageRequest = PageRequest.of(page,3);

        Page<Mission> result = missionRepository.findAllByStore(store,pageRequest);
        if(page>= result.getTotalPages()) throw new GeneralException(GeneralErrorCode.INVALID_PAGE_NUMBER);

        return MissionConverter.toMissionPreviewList(result);
    }
}
