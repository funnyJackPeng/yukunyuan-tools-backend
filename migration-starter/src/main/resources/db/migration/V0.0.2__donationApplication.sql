create table if not exists donation_application
(
    id
    serial
    primary
    key,
    user_info_id
    int
    not
    null,
    referrer_number
    varchar
(
    256
) not null,
    own_number varchar
(
    256
) not null,
    amount decimal
(
    16,
    2
) not null
    );

alter table donation_application
    add constraint donation_application_user_info_id_referrer_number_own_number_pk
        unique (user_info_id, referrer_number, own_number);

-- 修正 join_application 表中的联合主键名称
alter
index public.join_application_referrer_openid_number_own_number_pk rename to join_application_user_info_id_referrer_number_own_number_pk;
