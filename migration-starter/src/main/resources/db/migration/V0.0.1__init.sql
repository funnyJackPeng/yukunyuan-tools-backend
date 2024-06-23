create table if not exists "user"
(
    "id"          bigint primary key,
    "openid"      text,
    "session_key" text
);
