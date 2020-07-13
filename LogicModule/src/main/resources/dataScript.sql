CREATE TABLE `contacts_list_db`.`contact`
(
    `id`             BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `first_name`     VARCHAR(255) NOT NULL,
    `last_name`      VARCHAR(255) NOT NULL,
    `middle_name`    VARCHAR(255) NULL DEFAULT 'unknown',
    `birthday`       DATE         NULL,
    `sex`            VARCHAR(45)  NULL DEFAULT 'unknown',
    `nationality`    VARCHAR(45)  NULL DEFAULT 'unknown',
    `marital_status` VARCHAR(45)  NULL DEFAULT 'unknown',
    `url`            VARCHAR(255) NULL DEFAULT 'unknown',
    `email`          VARCHAR(255) NULL DEFAULT 'unknown',
    `job`            VARCHAR(255) NULL DEFAULT 'unknown',
    PRIMARY KEY (`id`)
);

CREATE TABLE `contacts_list_db`.`address`
(
    `id`         BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `country`    VARCHAR(45) NOT NULL,
    `city`       VARCHAR(45) NOT NULL,
    `street`     VARCHAR(45) NOT NULL,
    `post_index`      INT         NOT NULL,
    `contact_id` BIGINT(20)  NULL,
    PRIMARY KEY (`id`),
    INDEX `contact_id_idx` (`contact_id` ASC) VISIBLE,
    CONSTRAINT `contact_id`
        FOREIGN KEY (`contact_id`)
            REFERENCES `contacts_list_db`.`contact` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

INSERT INTO `contacts_list_db`.`contact`(first_name, last_name, middle_name, birthday, sex, nationality, marital_status, url, email, job)
VALUES ('Ivan', 'Ivanov', 'Ivanovich', '1990-01-03', 'male', 'Belarus', 'single', 'google.com', 'ivan@mail.ru',
        'iTechArt'),
       ('Jack', 'Klinton', 'Bobovich', '1993-02-01', 'male', 'USA', 'married', 'yandex.com', 'jack@mail.ru',
        'iTechArt'),
       ('Marina', 'Vaika', 'Viktorovna', '1992-11-03', 'female', 'Russia', 'married', 'google.com', 'marina@mail.ru',
        'EPAM'),
       ('Lexa', 'Lennon', 'Valerevich', '1988-01-04', 'male', 'Belarus', 'single', 'github.com', 'lexa@mail.ru',
        'Wargaming');

INSERT INTO `contacts_list_db`.`address` ( `country`, `city`, `street`, `post_index`, `contact_id`) VALUES
('Belarus', 'Minsk', 'Nemiga 20/42', '220040', '1'),
( 'USA', 'New York', '114 Indian Summer St.', '10016', '2'),
( 'Russia', 'Moscow', 'Lenina', '23234', '3'),
( 'Belarus', 'Minsk', 'Temeryazeva 23/15', '22334', '4');

