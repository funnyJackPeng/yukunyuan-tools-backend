create table if not exists user_info
(
    "id" serial primary key,
    "openid"      text,
    "session_key" text
);
