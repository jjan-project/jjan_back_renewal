
create table if not EXISTS chat (
                                    chat_id bigint not null auto_increment,
                                    message varchar(255),
    send_time varchar(255),
    sender varchar(255),
    sender_image varchar(255),
    party_id bigint,
    primary key (chat_id)
    ) engine=InnoDB;

create table if not EXISTS chat_room (
                                         chat_id bigint not null,
                                         create_date date,
                                         party_id bigint,
                                         primary key (chat_id)
    ) engine=InnoDB;

create table if not EXISTS party (
                                     id bigint not null auto_increment,
                                     created_at datetime(6),
    updated_at datetime(6),
    average_age bigint,
    content varchar(255) not null,
    last_chat varchar(255),
    address varchar(255),
    latitude float(53) not null,
    longitude float(53) not null,
    max_party_num integer not null,
    party_date datetime(6),
    title varchar(255) not null,
    user_id bigint,
    chat_room_chat_id bigint,
    primary key (id)
    ) engine=InnoDB;

create table if not EXISTS party_join (
                                          id bigint not null auto_increment,
                                          party_id bigint,
                                          user_id bigint,
                                          primary key (id)
    ) engine=InnoDB;

create table if not EXISTS party_entity_messages (
                                                     party_entity_id bigint not null,
                                                     checked bit not null,
                                                     message varchar(255)
    ) engine=InnoDB;

create table if not EXISTS party_entity_party_images (
                                                         party_entity_id bigint not null,
                                                         party_images varchar(255)
    ) engine=InnoDB;

create table if not EXISTS party_entity_party_tags (
                                                       party_entity_id bigint not null,
                                                       party_tags smallint
) engine=InnoDB;

create table if not EXISTS refresh_token (
                                             token_id bigint not null auto_increment,
                                             key_email varchar(255) not null,
    token varchar(255) not null,
    primary key (token_id)
    ) engine=InnoDB;

create table if not EXISTS user_tb (
                                       id bigint not null auto_increment,
                                       address varchar(255) not null,
    birth datetime(6) not null,
    drink_capacity varchar(255) not null,
    email varchar(255) not null,
    gender varchar(255) not null,
    is_nick_name_change_available bit,
    latitude float(53) not null,
    longitude float(53) not null,
    nick_name varchar(255) not null,
    password varchar(255) not null,
    profile varchar(255) not null,
    roles varchar(255) not null,
    primary key (id)
    ) engine=InnoDB;

alter table user_tb
    add constraint UK_2dlfg6wvnxboknkp9d1h75icb unique (email);

alter table user_tb
    add constraint UK_7mfej3vldgfb31kmch89xrqib unique (nick_name);

alter table chat
    add constraint FKan05u3weewkna57iteoykmqe2
        foreign key (party_id)
            references party (id);

alter table chat_room
    add constraint FKgfw8vmfngwdjbkvxxy4yl0yme
        foreign key (party_id)
            references party (id);

alter table party
    add constraint FK21kioijki9tuvmbk35qbg5wnx
        foreign key (user_id)
            references user_tb (id);

alter table party
    add constraint FK9e2t6roneg093huy2cqfpfuxk
        foreign key (chat_room_chat_id)
            references chat_room (chat_id);

alter table party_join
    add constraint FKsn2437jnyhmxbtk10tx1jv7lk
        foreign key (party_id)
            references party (id);

alter table party_join
    add constraint FK3ixmjihr705s9nnk4v82j53ug
        foreign key (user_id)
            references user_tb (id);

alter table party_entity_messages
    add constraint FK254w6h1vba5jetcte2q1vhac4
        foreign key (party_entity_id)
            references party (id);

alter table party_entity_party_images
    add constraint FK6owvfxf5o0k22turnshfrdfg7
        foreign key (party_entity_id)
            references party (id);

alter table party_entity_party_tags
    add constraint FKs263embgulpr3cj283od0r4ve
        foreign key (party_entity_id)
            references party (id);