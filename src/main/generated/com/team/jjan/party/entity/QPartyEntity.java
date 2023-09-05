package com.team.jjan.party.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPartyEntity is a Querydsl query type for PartyEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPartyEntity extends EntityPathBase<PartyEntity> {

    private static final long serialVersionUID = 100377059L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPartyEntity partyEntity = new QPartyEntity("partyEntity");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final com.team.jjan.user.entitiy.QUserEntity author;

    public final NumberPath<Long> averageAge = createNumber("averageAge", Long.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.team.jjan.partyJoin.entity.PartyJoin, com.team.jjan.partyJoin.entity.QPartyJoin> joinUser = this.<com.team.jjan.partyJoin.entity.PartyJoin, com.team.jjan.partyJoin.entity.QPartyJoin>createList("joinUser", com.team.jjan.partyJoin.entity.PartyJoin.class, com.team.jjan.partyJoin.entity.QPartyJoin.class, PathInits.DIRECT2);

    public final QLocation location;

    public final NumberPath<Integer> maxPartyNum = createNumber("maxPartyNum", Integer.class);

    public final ListPath<Message, QMessage> messages = this.<Message, QMessage>createList("messages", Message.class, QMessage.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> partyDate = createDateTime("partyDate", java.util.Date.class);

    public final ListPath<String, StringPath> partyImages = this.<String, StringPath>createList("partyImages", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<PartyTag, EnumPath<PartyTag>> partyTags = this.<PartyTag, EnumPath<PartyTag>>createList("partyTags", PartyTag.class, EnumPath.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPartyEntity(String variable) {
        this(PartyEntity.class, forVariable(variable), INITS);
    }

    public QPartyEntity(Path<? extends PartyEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPartyEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPartyEntity(PathMetadata metadata, PathInits inits) {
        this(PartyEntity.class, metadata, inits);
    }

    public QPartyEntity(Class<? extends PartyEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.team.jjan.user.entitiy.QUserEntity(forProperty("author")) : null;
        this.location = inits.isInitialized("location") ? new QLocation(forProperty("location")) : null;
    }

}

