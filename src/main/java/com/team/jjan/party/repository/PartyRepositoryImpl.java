package com.team.jjan.party.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.jjan.party.dto.request.PartySearchCondition;
import com.team.jjan.party.dto.request.SortSelection;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.entity.PartyTag;
import com.team.jjan.party.entity.QPartyEntity;
import com.team.jjan.partyJoin.entity.QPartyJoin;
import com.team.jjan.user.entitiy.QUserEntity;
import com.team.jjan.user.entitiy.UserEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.team.jjan.party.entity.QPartyEntity.*;
import static com.team.jjan.partyJoin.entity.QPartyJoin.*;
import static com.team.jjan.user.entitiy.QUserEntity.*;

public class PartyRepositoryImpl implements PartyRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PartyRepositoryImpl(EntityManager em){
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PartyEntity> findAllBySearch(Pageable pageable, PartySearchCondition searchCondition, UserEntity user) {
        return queryFactory
                .selectFrom(partyEntity)
                .leftJoin(partyEntity.author, userEntity).fetchJoin()
                .leftJoin(partyEntity.joinUser, partyJoin).fetchJoin()
                .leftJoin(partyJoin.joinUser).fetchJoin()
                .where(
                    useTag(searchCondition.getPartyTagList()),      //어떤 술 모임에 가고 싶으세요?
                    partition(searchCondition.getPersonnelGoe(), searchCondition.getPersonnelLoe())        //모집 인원을 선택해주세요
                )
                .groupBy(partyEntity.id)
                .orderBy(
                    sorting(searchCondition.getSort())              //정렬
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression partition(Integer personnelGoe, Integer personnelLoe) {
        return partyEntity.maxPartyNum.between(personnelGoe, personnelLoe);
    }

    private BooleanExpression useTag(List<PartyTag> partyTagList) {
        return Expressions.anyOf(partyTagList.stream().map(this::isFilteredPartyTag).toArray(BooleanExpression[]::new));
    }

    private BooleanExpression isFilteredPartyTag(PartyTag partyTag){
        return partyEntity.partyTags.contains(partyTag);
    }

    private OrderSpecifier<?> sorting(SortSelection sort) {

//        if(sort.getSorting().equals("가까운 위치 순")){
//
//        }
        return partyEntity.partyDate.asc();

    }
}
