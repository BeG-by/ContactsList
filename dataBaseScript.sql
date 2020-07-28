DROP SCHEMA IF EXISTS `contacts_list_db`;
CREATE SCHEMA `contacts_list_db` DEFAULT CHARACTER SET utf8mb4;
USE `contacts_list_db`;

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contacts_list_db`.`contact`
(
    `id`             BIGINT(20) UNSIGNED        NOT NULL AUTO_INCREMENT,
    `first_name`     VARCHAR(255)               NOT NULL,
    `last_name`      VARCHAR(255)               NOT NULL,
    `middle_name`    VARCHAR(255)               NULL,
    `birthday`       DATE                       NULL,
    `sex`            ENUM ('male', 'female')    NULL,
    `nationality`    VARCHAR(45)                NULL,
    `marital_status` ENUM ('married', 'single') NULL,
    `url`            VARCHAR(255)               NULL,
    `email`          VARCHAR(45)                NULL,
    `job`            VARCHAR(45)                NULL,
    `image_name`     VARCHAR(255)               NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



DROP TABLE IF EXISTS `address`;

CREATE TABLE `contacts_list_db`.`address`
(
    `id`         BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `country`    VARCHAR(45)         NULL,
    `city`       VARCHAR(45)         NULL,
    `street`     VARCHAR(45)         NULL,
    `post_index` INT                 NULL,
    `contact_id` BIGINT(20) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `contact_id_idx` (`contact_id` ASC) VISIBLE,
    CONSTRAINT `contact_address_id`
        FOREIGN KEY (`contact_id`)
            REFERENCES `contacts_list_db`.`contact` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


DROP TABLE IF EXISTS `phone`;

CREATE TABLE `contacts_list_db`.`phone`
(
    `id`            BIGINT(20) UNSIGNED     NOT NULL AUTO_INCREMENT,
    `contact_id`    BIGINT(20) UNSIGNED     NOT NULL,
    `country_code`  INT                     NULL,
    `operator_code` INT                     NULL,
    `number`        INT                     NULL,
    `type`          ENUM ('mobile', 'home') NULL,
    `comment`       VARCHAR(255)            NULL,
    PRIMARY KEY (`id`),
    INDEX `contact_id_idx` (`contact_id` ASC) VISIBLE,
    CONSTRAINT `contact_phone_id`
        FOREIGN KEY (`contact_id`)
            REFERENCES `contacts_list_db`.`contact` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


DROP TABLE IF EXISTS `attachment`;

CREATE TABLE `contacts_list_db`.`attachment`
(
    `id`           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `contact_id`   BIGINT(20) UNSIGNED NOT NULL,
    `file_name`    VARCHAR(255)        NOT NULL,
    `date_of_load` date                NOT NULL,
    `comment`      VARCHAR(255)        NULL,
    PRIMARY KEY (`id`),
    INDEX `contact_id_idx` (`contact_id` ASC) VISIBLE,
    CONSTRAINT `contact_attachment_id`
        FOREIGN KEY (`contact_id`)
            REFERENCES `contacts_list_db`.`contact` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



# drop procedure if exists fillContacts;
#
# delimiter $$$
# CREATE PROCEDURE fillContacts()
# BEGIN
#     DECLARE i int DEFAULT 0;
#     WHILE i <= 15
#         DO
#             INSERT INTO `contacts_list_db`.`contact`(first_name, last_name, middle_name, birthday, sex, nationality,
#                                                      marital_status,
#                                                      url, job, image_name)
#             VALUES ('Ivan', 'Ivanov', 'Ivanovich', '1990-01-03', 'male', 'Belarus', 'single', 'google.com',
#                     'iTechArt', 'fileNameTest.png'),
#                    ('Jack', 'Klinton', 'Bobovich', '1993-02-01', 'male', 'USA', 'married', 'yandex.com',
#                     'iTechArt', 'fileNameTest.png'),
#                    ('Marina', 'Vaika', 'Viktorovna', '1992-11-03', 'female', 'Russia', 'married', 'google.com',
#                     'EPAM', 'fileNameTest.png'),
#                    ('Lexa', 'Lennon', 'Valerevich', '1988-01-04', 'male', 'Belarus', 'single', 'github.com',
#                     'Wargaming', 'fileNameTest.png');
#             SET i = i + 1;
#         END WHILE;
# END $$$
# delimiter ;
#
# call fillContacts();
#
#
# drop procedure if exists fillAddress;
#
# delimiter $$$
# CREATE PROCEDURE fillAddress()
# BEGIN
#     DECLARE i int DEFAULT 1;
#     WHILE i <= 60
#         DO
#             INSERT INTO `contacts_list_db`.`address` (`country`, `city`, `street`, `post_index`, `contact_id`)
#             VALUES ('Belarus', 'Minsk', 'Nemiga 20/42', '220040', i);
#             SET i = i + 1;
#             INSERT INTO `contacts_list_db`.`address` (`country`, `city`, `street`, `post_index`, `contact_id`)
#             VALUES ('USA', 'New York', '114 Indian Summer St.', '10016', i);
#             SET i = i + 1;
#             INSERT INTO `contacts_list_db`.`address` (`country`, `city`, `street`, `post_index`, `contact_id`)
#             VALUES ('Russia', 'Moscow', 'Lenina', '23234', i);
#             SET i = i + 1;
#             INSERT INTO `contacts_list_db`.`address` (`country`, `city`, `street`, `post_index`, `contact_id`)
#             VALUES ('Belarus', 'Minsk', 'Temeryazeva 23/15', '22334', i);
#             SET i = i + 1;
#         END WHILE;
# END $$$
# delimiter ;
#
# call fillAddress();
