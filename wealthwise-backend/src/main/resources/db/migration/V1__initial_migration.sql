
CREATE DATABASE IF NOT EXISTS  `wealthwisedb`;
USE `wealthwisedb`;

create table if not exists users
(
    id                  bigint auto_increment
        primary key,
    email               varchar(255)                                         not null,
    name                varchar(255)                                         not null,
    password            varchar(255)                                         not null,
    verification_status enum ('UNVERIFIED', 'VERIFIED') default 'UNVERIFIED' not null,
    phone_number        varchar(20)                                          not null
);

create table if not exists user_banking
(
    id                  bigint                                                                                  not null
        primary key,
    access_token        varchar(255)                                                                            null,
    account_link_status enum ('LINK_STATUS_NOT_CREATED', 'LINKED', 'PENDING') default 'LINK_STATUS_NOT_CREATED' not null,
    constraint user_banking_users_id_fk
        foreign key (id) references users (id)
);

