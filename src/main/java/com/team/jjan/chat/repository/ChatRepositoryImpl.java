package com.team.jjan.chat.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.jjan.chat.dto.ChatResponse;
import com.team.jjan.chat.dto.ChatRoomResponse;
import com.team.jjan.party.entity.QPartyEntity;
import com.team.jjan.partyJoin.entity.PartyJoin;
import com.team.jjan.partyJoin.entity.QPartyJoin;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.jjan.user.entitiy.QUserEntity.userEntity;
import static com.team.jjan.partyJoin.entity.QPartyJoin.partyJoin;
import static com.team.jjan.chat.entity.QChatRoom.chatRoom;
import static com.team.jjan.party.entity.QPartyEntity.partyEntity;
import static com.team.jjan.chat.entity.QChat.chat;

@RequiredArgsConstructor
public class ChatRepositoryImpl implements CustomChatRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatResponse> findChatDataByPartyId(long meetingId) {
        return jpaQueryFactory.select(Projections.constructor(
                        ChatResponse.class,
                        chat.chatId,
                        chat.sender,
                        chat.message,
                        chat.sendTime,
                        chat.senderImage
                )).from(chat)
                .innerJoin(chat.party , partyEntity).on(partyEntity.id.eq(meetingId))
                .fetch();
    }

    @Override
    public List<ChatRoomResponse> findChatRoomByUserEmail(String userEmail) {
        // 내가 속해있는 파티 룸

        List<ChatRoomResponse> result = jpaQueryFactory.select(Projections.constructor(
                        ChatRoomResponse.class,
                        chatRoom.chatId,
                        partyEntity.id,
                        partyEntity.title
                )).from(partyJoin)
                .innerJoin(partyJoin.joinParty , partyEntity)
                .innerJoin(partyJoin.joinUser , userEntity).on(userEntity.email.eq(userEmail))
                .leftJoin(partyEntity.chatRoom , chatRoom)
                .fetch();

        // 내가 생성한 파티 룸
        result.add(jpaQueryFactory.select(Projections.constructor(
                        ChatRoomResponse.class,
                        chatRoom.chatId,
                        partyEntity.id,
                        partyEntity.title
                )).from(partyEntity)
                .innerJoin(partyEntity.author, userEntity).on(userEntity.email.eq(userEmail))
                .innerJoin(partyEntity.chatRoom , chatRoom)
                .fetchOne());

        return result;
    }

}