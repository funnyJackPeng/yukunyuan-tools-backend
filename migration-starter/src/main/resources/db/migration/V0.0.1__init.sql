create table if not exists user_info
(
    "id"        serial primary key,
    "openid"      text,
    "user_name" varchar(256),
    "session_key" text
);
alter table user_info
    add constraint user_info_openid_pk
        unique (openid);
