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

INSERT INTO `crud_spring`.`users` (`email`, `firstName`, `lastName`, `birthDate`) VALUES ('janedoe@example.com', 'Jane', 'Doe', '2000-01-01');
INSERT INTO `crud_spring`.`users` (`email`, `firstName`, `lastName`, `birthDate`) VALUES ('johndoe@example.com', 'John', 'Doe', '2000-01-01');
INSERT INTO `crud_spring`.`users` (`email`, `firstName`, `lastName`, `birthDate`) VALUES ('elonmusk@example.com', 'Elon', 'Mask', '1970-01-01');
INSERT INTO `crud_spring`.`users` (`email`, `firstName`, `lastName`, `birthDate`) VALUES ('joe@example.com', 'Joe', 'Biden', '1900-07-13');
INSERT INTO `crud_spring`.`users` (`email`, `firstName`, `lastName`, `birthDate`) VALUES ('indi@example.com', 'Henry', 'Jones', '1942-07-13');
INSERT INTO `crud_spring`.`users` (`email`, `firstName`, `lastName`, `birthDate`) VALUES ('ford@example.com', 'Henry', 'Ford', '1863-07-30');

INSERT INTO`crud_spring`.`roles`(id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

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
