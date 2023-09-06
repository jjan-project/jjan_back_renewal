package com.team.jjan.partyJoin.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPartyJoin is a Querydsl query type for PartyJoin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPartyJoin extends EntityPathBase<PartyJoin> {

    private static final long serialVersionUID = -274246752L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPartyJoin partyJoin = new QPartyJoin("partyJoin");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.team.jjan.party.entity.QPartyEntity joinParty;

    public final com.team.jjan.user.entitiy.QUserEntity joinUser;

    public QPartyJoin(String variable) {
        this(PartyJoin.class, forVariable(variable), INITS);
    }

    public QPartyJoin(Path<? extends PartyJoin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPartyJoin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPartyJoin(PathMetadata metadata, PathInits inits) {
        this(PartyJoin.class, metadata, inits);
    }

    public QPartyJoin(Class<? extends PartyJoin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.joinParty = inits.isInitialized("joinParty") ? new com.team.jjan.party.entity.QPartyEntity(forProperty("joinParty"), inits.get("joinParty")) : null;
        this.joinUser = inits.isInitialized("joinUser") ? new com.team.jjan.user.entitiy.QUserEntity(forProperty("joinUser")) : null;
    }

}

