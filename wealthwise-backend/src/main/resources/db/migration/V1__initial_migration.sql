
CREATE DATABASE IF NOT EXISTS  `wealthwisedb`;
USE `wealthwisedb`;


CREATE TABLE IF NOT EXISTS `users` (
                                       `id` BIGINT NOT NULL AUTO_INCREMENT,
                                       `email` VARCHAR(255) NOT NULL,
                                       `name` VARCHAR(255) NOT NULL,
                                       `password` VARCHAR(255) NOT NULL,
                                       `access_token` VARCHAR(512) NOT NULL,
                                       `verification_status` ENUM('UNVERIFIED', 'VERIFIED') NOT NULL DEFAULT 'UNVERIFIED',
                                       `account_link_status` ENUM('LINK_STATUS_NOT_CREATED', 'LINKED', 'PENDING') NOT NULL DEFAULT 'LINK_STATUS_NOT_CREATED',
                                       `phone_number` VARCHAR(20) NOT NULL,
                                       PRIMARY KEY (`id`)
);