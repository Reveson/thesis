create database thesis;
use thesis;

CREATE TABLE user
(
    id         int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login      varchar(255) NOT NULL,
    name       varchar(255),
    surname    varchar(255),
    city       varchar(255),
    birth_date date,
    privileges tinyint      NOT NULL,
    is_private boolean,
    UNIQUE (login)
);