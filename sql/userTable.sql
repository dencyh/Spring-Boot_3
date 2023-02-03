CREATE DATABASE IF NOT EXISTS crud_spring;

USE tutorialDb;

CREATE TABLE IF NOT EXISTS `crud_spring`.`users`
(
    `id`        INT          NOT NULL AUTO_INCREMENT,
    `email`     VARCHAR(64)  NOT NULL,
    `firstName` VARCHAR(128) NOT NULL,
    `lastName`  VARCHAR(128) NOT NULL,
    `birthDate` DATE         NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
)
    DEFAULT CHARACTER SET = utf8;
