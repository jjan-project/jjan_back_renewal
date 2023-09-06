package com.team.jjan.party.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMessage extends BeanPath<Message> {

    private static final long serialVersionUID = 471517313L;

    public static final QMessage message1 = new QMessage("message1");

    public final BooleanPath checked = createBoolean("checked");

    public final StringPath message = createString("message");

    public QMessage(String variable) {
        super(Message.class, forVariable(variable));
    }

    public QMessage(Path<? extends Message> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMessage(PathMetadata metadata) {
        super(Message.class, metadata);
    }

}

