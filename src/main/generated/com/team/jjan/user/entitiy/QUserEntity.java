package com.team.jjan.user.entitiy;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = 1675747804L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final StringPath address = createString("address");

    public final DateTimePath<java.util.Date> birth = createDateTime("birth", java.util.Date.class);

    public final ListPath<com.team.jjan.party.entity.PartyEntity, com.team.jjan.party.entity.QPartyEntity> createParty = this.<com.team.jjan.party.entity.PartyEntity, com.team.jjan.party.entity.QPartyEntity>createList("createParty", com.team.jjan.party.entity.PartyEntity.class, com.team.jjan.party.entity.QPartyEntity.class, PathInits.DIRECT2);

    public final StringPath drinkCapacity = createString("drinkCapacity");

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isNickNameChangeAvailable = createBoolean("isNickNameChangeAvailable");

    public final ListPath<com.team.jjan.partyJoin.entity.PartyJoin, com.team.jjan.partyJoin.entity.QPartyJoin> joinParty = this.<com.team.jjan.partyJoin.entity.PartyJoin, com.team.jjan.partyJoin.entity.QPartyJoin>createList("joinParty", com.team.jjan.partyJoin.entity.PartyJoin.class, com.team.jjan.partyJoin.entity.QPartyJoin.class, PathInits.DIRECT2);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath profile = createString("profile");

    public final EnumPath<Role> roles = createEnum("roles", Role.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

