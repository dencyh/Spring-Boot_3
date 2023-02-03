CREATE DATABASE IF NOT EXISTS crud_spring;
USE crud_spring;

CREATE TABLE IF NOT EXISTS `crud_spring`.`users`
(
    `id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `email`     VARCHAR(64)  NOT NULL,
    `firstName` VARCHAR(128) NOT NULL,
    `lastName`  VARCHAR(128) NOT NULL,
    `birthDate` DATE         NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 19
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `crud_spring`.`roles`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `crud_spring`.`roles_users`
(
    `roleId` BIGINT NOT NULL,
    `userId` BIGINT NOT NULL,
    PRIMARY KEY (`roleId`, `userId`),
    CONSTRAINT `userId`
        FOREIGN KEY (`userId`)
            REFERENCES `crud_spring`.`users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `roleId`
        FOREIGN KEY (`roleId`)
            REFERENCES `crud_spring`.`roles` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
