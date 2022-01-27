use qldp;

CREATE TABLE `petitions`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `sender_id` INT DEFAULT NULL,
    `subject` varchar(255) COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `content` TEXT COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `date` date DEFAULT NULL,
    `status` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE `replies`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `replier_id` INT DEFAULT NULL,
    `petition_id` INT DEFAULT NULL,
    `subject` varchar(255) COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `content` TEXT COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `date` date DEFAULT NULL,
    `status` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

ALTER TABLE `petitions`
    ADD CONSTRAINT `petitions_sender_id` FOREIGN KEY (`sender_id`) REFERENCES `users` (`ID`);

ALTER TABLE `replies`
    ADD CONSTRAINT `replies_replier_id` FOREIGN KEY (`replier_id`) REFERENCES `users` (`ID`),
    ADD CONSTRAINT `replies_petition_id` FOREIGN KEY (`petition_id`) REFERENCES `petitions` (`ID`);
