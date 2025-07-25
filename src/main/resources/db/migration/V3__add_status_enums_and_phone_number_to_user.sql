alter table users
    add verification_status varchar(20) default 'UNVERIFIED' not null;

alter table users
    add account_link_status varchar(50) default 'LINK_STATUS_NOT_CREATED' not null;

alter table users
    add phone_number varchar(20) not null;