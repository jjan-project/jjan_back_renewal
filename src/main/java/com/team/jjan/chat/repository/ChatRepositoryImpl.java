package com.team.jjan.chat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.jjan.chat.dto.ChatResponse;
import com.team.jjan.chat.dto.ChatRoomResponse;
import com.team.jjan.party.entity.QPartyEntity;
import com.team.jjan.partyJoin.entity.QPartyJoin;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import static com.team.jjan.user.entitiy.QUserEntity.userEntity;
import static com.team.jjan.partyJoin.entity.QPartyJoin.partyJoin;
import static com.team.jjan.chat.entity.QChatRoom.chatRoom;
import static com.team.jjan.party.entity.QPartyEntity.partyEntity;
import static com.team.jjan.chat.entity.QChat.chat;

public class ChatRepositoryImpl implements CustomChatRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ChatRepositoryImpl(EntityManager em){
        jpaQueryFactory = new JPAQueryFactory(em);
    }

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
        List<ChatRoomResponse> result = jpaQueryFactory.select(Projections.constructor(
                        ChatRoomResponse.class,
                        chatRoom.chatId,
                        partyEntity.id,
                        partyEntity.title,
                        partyEntity.partyImages
                )).from(partyEntity)
                .innerJoin(partyEntity.chatRoom , chatRoom)
                .leftJoin(partyEntity.joinUser , partyJoin)
                .leftJoin(partyJoin.joinUser , userEntity).on(userEntity.email.eq(userEmail))
                .fetch();

        result.add(jpaQueryFactory.select(Projections.constructor(
                        ChatRoomResponse.class,
                        chatRoom.chatId,
                        partyEntity.id,
                        partyEntity.title,
                        partyEntity.partyImages
                )).from(partyEntity)
                .innerJoin(partyEntity.author, userEntity).on(userEntity.email.eq(userEmail))
                .innerJoin(partyEntity.chatRoom , chatRoom)
                .fetchOne());

        return result;
    }

}