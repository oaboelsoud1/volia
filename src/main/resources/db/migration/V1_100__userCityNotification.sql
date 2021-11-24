CREATE TABLE IF NOT EXISTS notification_hibernate_sequence
(
    id             int auto_increment primary key,
    sequence_name  varchar(50) not null,
    sequence_value bigint      not null
);



DROP table if exists city;
CREATE TABLE city
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    creation_date     datetime              NOT NULL,
    modification_date datetime              NULL,
    created_by        BIGINT                NULL,
    modified_by       BIGINT                NULL,
    name              VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_city PRIMARY KEY (id)
);

ALTER TABLE city
    ADD CONSTRAINT uc_city_id UNIQUE (id);

ALTER TABLE city
    ADD CONSTRAINT uc_city_name UNIQUE (name);

DROP table if exists user;
CREATE TABLE user
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    creation_date           datetime              NOT NULL,
    modification_date       datetime              NULL,
    created_by              BIGINT                NULL,
    modified_by             BIGINT                NULL,
    name                    VARCHAR(255)          NULL,
    city_id                 BIGINT                NOT NULL,
    notification_preference VARCHAR(50)           NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_id UNIQUE (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_CITY FOREIGN KEY (city_id) REFERENCES city (id);



DROP table if exists notification;
CREATE TABLE notification
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    text VARCHAR(600)          NOT NULL,
    code VARCHAR(255)          NOT NULL,

    CONSTRAINT pk_notification PRIMARY KEY (id)
);

ALTER TABLE notification
    ADD CONSTRAINT uc_notification_id UNIQUE (id);


DROP table if exists user_notification_log;
CREATE TABLE user_notification_log
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    creation_date           datetime              NOT NULL,
    modification_date       datetime              NULL,
    created_by              BIGINT                NULL,
    modified_by             BIGINT                NULL,
    recipient_id            BIGINT(255)           NOT NULL,
    notification_code       VARCHAR(255)          NULL,
    notification_preference VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_user_notification_log PRIMARY KEY (id)
);

insert into notification_hibernate_sequence (sequence_name, sequence_value)
values ('notification_code_sequence', 0);

ALTER TABLE user_notification_log
    ADD CONSTRAINT uc_user_notification_log_id UNIQUE (id);
DROP PROCEDURE IF EXISTS doGetNotificationSequenceNextValue;
DELIMITER $$
CREATE PROCEDURE `doGetNotificationSequenceNextValue`(IN sequence_in varchar(50), OUT next_out bigint)
BEGIN
    SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;
    UPDATE notification_hibernate_sequence
    SET sequence_value = @amount := sequence_value + 1
    WHERE sequence_name = sequence_in
    LIMIT 1;

    set next_out = @amount;
END $$
DELIMITER ;