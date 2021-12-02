CREATE DATABASE thesis;
use thesis;

CREATE TABLE user
(
    id         int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login      varchar(255) NOT NULL,
    name       varchar(255),
    surname    varchar(255),
    city       varchar(255),
    birth_date date,
    is_blocked boolean      NOT NULL DEFAULT FALSE,
    UNIQUE (login)
);