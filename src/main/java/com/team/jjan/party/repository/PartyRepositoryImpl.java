package com.team.jjan.party.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.jjan.party.dto.request.AgeTag;
import com.team.jjan.party.dto.request.PartySearchCondition;
import com.team.jjan.party.dto.request.SortSelection;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.entity.PartyTag;
import com.team.jjan.user.entitiy.UserEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.team.jjan.party.entity.QPartyEntity.*;
import static com.team.jjan.partyJoin.entity.QPartyJoin.*;
import static com.team.jjan.user.entitiy.QUserEntity.*;

public class PartyRepositoryImpl implements PartyRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PartyRepositoryImpl(EntityManager em){
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PartyEntity> findAllBySearch(Pageable pageable, PartySearchCondition searchCondition, UserEntity user) {
        List<PartyEntity> content = queryFactory
            .selectFrom(partyEntity)
            .leftJoin(partyEntity.author, userEntity).fetchJoin()
            .leftJoin(partyEntity.joinUser, partyJoin).fetchJoin()
            .leftJoin(partyJoin.joinUser).fetchJoin()
            .where(
                useTag(searchCondition.getPartyTagList()),
                //어떤 술 모임에 가고 싶으세요?
                partition(searchCondition.getPersonnelGoe(), searchCondition.getPersonnelLoe()),
                //모집 인원을 선택해주세요
                radius(searchCondition.getRadiusRange(), user),
                //동네 반경 범위
                ageTag(searchCondition.getAgeTag())
                //연령대
            )
            .orderBy(
                sorting(searchCondition.getSort(), user)
                //정렬
            )
            .fetch();

        Long total = queryFactory
            .select(partyEntity.count())
            .from(partyEntity)
            .leftJoin(partyEntity.author, userEntity).fetchJoin()
            .leftJoin(partyEntity.joinUser, partyJoin).fetchJoin()
            .leftJoin(partyJoin.joinUser).fetchJoin()
            .where(
                useTag(searchCondition.getPartyTagList()),
                //어떤 술 모임에 가고 싶으세요?
                partition(searchCondition.getPersonnelGoe(), searchCondition.getPersonnelLoe()),
                //모집 인원을 선택해주세요
                radius(searchCondition.getRadiusRange(), user),
                //동네 반경 범위
                ageTag(searchCondition.getAgeTag())
                //연령대
            )
            .orderBy(
                sorting(searchCondition.getSort(), user)
                //정렬
            )
            .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Optional<PartyEntity> findPartyAndChatById(long partyId) {
        PartyEntity result = queryFactory
                .select(partyEntity)
                .from(partyEntity)
                .leftJoin(partyEntity.chatList).fetchJoin()
                .where(partyEntity.id.eq(partyId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    private BooleanExpression useTag(List<PartyTag> partyTagList) {
        return !partyTagList.isEmpty()?Expressions.allOf(partyTagList.stream().map(this::isFilteredPartyTag).toArray(BooleanExpression[]::new)):null;
    }

    private BooleanExpression isFilteredPartyTag(PartyTag partyTag){
        return partyEntity.partyTags.contains(partyTag);
    }

    private BooleanExpression ageTag(List<AgeTag> ageTagList) {
        return !ageTagList.isEmpty()?Expressions.anyOf(ageTagList.stream().map(this::ageTag).toArray(BooleanExpression[]::new)):null;
    }

    private BooleanExpression ageTag(AgeTag ageTag) {
        return ageTag!=null?partyEntity.averageAge.between(ageTag.getStartAge(), ageTag.getEndAge()):null;
    }

    private BooleanExpression partition(Integer personnelGoe, Integer personnelLoe) {
        return !(personnelGoe==null||personnelLoe==null)?partyEntity.maxPartyNum.between(personnelGoe, personnelLoe):null;
    }

    private BooleanExpression radius(Integer radiusRange, UserEntity user) {
        if(radiusRange==null) return null;

        NumberExpression<Double> theta = partyEntity.location.longitude.add(-user.getLongitude());
        NumberExpression<Double> dist = MathExpressions.sin(DegreeToRadianExpression(partyEntity.location.latitude))
                .multiply(Math.sin(DegreeToRadian(user.getLatitude())))
                .add(
                        MathExpressions.cos(DegreeToRadianExpression(partyEntity.location.latitude))
                                .multiply(Math.cos(DegreeToRadian(user.getLatitude())))
                                .multiply(MathExpressions.cos(DegreeToRadianExpression(theta)))
                );

        dist = MathExpressions.acos(dist);
        dist = RadianToDegreeExpression(dist);
        dist = dist.multiply(60*1.1515*1.609344);

        return dist.loe(radiusRange);
    }

    private NumberExpression<Double> DegreeToRadianExpression(NumberExpression<Double> degree) {
        return degree.multiply(Math.PI/180.0);
    }

    private NumberExpression<Double> RadianToDegreeExpression(NumberExpression<Double> radian) {
        return radian.multiply(180/Math.PI);
    }

    private double DegreeToRadian(double degree) {
        return degree * (Math.PI / 180.0);
    }

    private OrderSpecifier<?> sorting(SortSelection sort, UserEntity user) {

        if(sort.getSorting().equals("가까운 위치 순")){
            return partyEntity.location.latitude.add(-user.getLatitude()).abs()
                    .add(partyEntity.location.longitude.add(-user.getLongitude()).abs()).asc();
        }
        return partyEntity.partyDate.asc();

    }
}
