package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.dto.SelectedMissionInfo;
import com.example.umc9th.domain.mission.dto.UnselectedMissionInfo;
import com.example.umc9th.domain.mission.entity.QMission;
import com.example.umc9th.domain.mission.entity.mapping.QMemberMission;
import com.example.umc9th.domain.store.entity.QDistrict;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberMissionRepositoryImpl implements MemberMissionRepositoryCustom{


    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SelectedMissionInfo> findSelectedMissionsWithCursor(Long memberId, String cursor, Boolean isCompleted, Integer pageSize) {

        QMemberMission mm = QMemberMission.memberMission;
        QMission m = QMission.mission;
        QStore s = QStore.store;

        StringExpression cursorValue = StringExpressions.lpad(m.point.stringValue(),10,'0')
                .concat(StringExpressions.lpad(mm.id.stringValue(),10,'0'));

        List<SelectedMissionInfo> result = jpaQueryFactory.select(Projections.constructor(SelectedMissionInfo.class,
                mm.id.as("memberMissionId"), s.name.as("storeName"), m.description.as("missionDescription"), m.point.as("point"),
                mm.isCompleted.as("is_completed")))
                .from(mm).join(mm.mission,m).join(m.store,s)
                .where(mm.member.id.eq(memberId).and(cursor != null ? cursorValue.lt(cursor):null).and(mm.isCompleted.eq(isCompleted)))
                .orderBy(m.point.desc(),mm.id.desc())
                .limit(pageSize)
                .fetch();
        return result;
    }

    @Override
    public Long findCompletedMissionCountByDistrict(Long memberId,String district) {

        QMemberMission mm = QMemberMission.memberMission;
        QMission m = QMission.mission;
        QStore s = QStore.store;
        QDistrict d = QDistrict.district;

        Long result = jpaQueryFactory.select(mm.count()).from(mm).join(mm.mission, m).join(m.store, s).join(s.district, d)
                .where(mm.member.id.eq(memberId).and(mm.isCompleted.eq(true)).and(d.name.eq(district)))
                .fetchOne();

        return result;
    }

    @Override
    public List<UnselectedMissionInfo> findUnselectedMissionsByDistrictWithCursor(Long memberId, String cursor, String district, Integer pageSize) {

        QMemberMission mm = QMemberMission.memberMission;
        QMission m = QMission.mission;
        QStore s = QStore.store;
        QDistrict d = QDistrict.district;

        StringExpression cursorValue = StringExpressions.lpad(m.point.stringValue(),10,'0')
                .concat(StringExpressions.lpad(m.id.stringValue(),10,'0'));

        List<UnselectedMissionInfo> result = jpaQueryFactory.select(Projections.constructor(UnselectedMissionInfo.class,
                m.id.as("missionId"),s.name.as("storeName"),m.description.as("missionDescription"),m.point,m.deadline))
                .from(s).join(s.district,d).join(m).on(s.id.eq(m.store.id)).leftJoin(mm).on(m.id.eq(mm.mission.id).
                        and(mm.member.id.eq(memberId)))
                .where(d.name.eq(district).and(mm.id.isNull()).and(cursor !=null ? cursorValue.lt(cursor):null))
                .orderBy(m.point.desc(),m.id.desc())
                .limit(pageSize).fetch();

        return result;
    }
}
