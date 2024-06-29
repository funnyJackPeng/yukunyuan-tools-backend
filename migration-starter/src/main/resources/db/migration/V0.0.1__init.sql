create table if not exists user_info
(
    id          serial primary key,
    openid      text         not null,
    user_name   varchar(256) not null,
    session_key text         not null
);
alter table user_info
    add constraint user_info_openid_pk
        unique (openid);

create table if not exists join_application
(
    id              serial primary key,
    user_info_id    int            not null,
    referrer_number varchar(256)   not null,
    own_number      varchar(256)   not null,
    amount          decimal(16, 2) not null,
    surname         varchar(3)     not null,
    gender          varchar(3)     not null,
    nick_name       varchar(256)   not null,
    address         varchar(512)   not null
);
alter table join_application
    add constraint join_application_referrer_openid_number_own_number_pk
        unique (user_info_id, referrer_number, own_number);
