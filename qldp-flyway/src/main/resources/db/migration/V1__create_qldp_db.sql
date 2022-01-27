use qldp;

CREATE TABLE `hibernate_sequence`
(
    `next_val` BIGINT
) ENGINE = InnoDB;

INSERT INTO `hibernate_sequence` VALUES (2);

CREATE TABLE `id_card`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `person_id` INT DEFAULT NULL,
    `id_card_number` varchar(12) COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `issued_day` date DEFAULT NULL,
    `issued_place` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE `corrections`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `household_id` INT DEFAULT NULL,
    `change_info` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `change_from` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `change_to` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `change_day` date DEFAULT NULL,
    `performer_id` INT DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE `family`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `person_id` INT DEFAULT NULL,
    `full_name` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `birthday` date DEFAULT NULL,
    `sex` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `person_relation` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `job` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `current_address` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE `household`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `household_code` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `host_id` INT DEFAULT NULL,
    `area_code` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `address` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `created_day` date DEFAULT NULL,
    `leave_day` date DEFAULT NULL,
    `leave_reason` text COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `performer_id` INT DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE `death`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `death_cert_number` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `declared_person_id` INT DEFAULT NULL,
    `death_person_id` INT DEFAULT NULL,
    `declared_day` date DEFAULT NULL,
    `death_day` date DEFAULT NULL,
    `death_reason` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE `people`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `people_code` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `full_name` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `alias` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `birthday` date DEFAULT NULL,
    `sex` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `birth_place` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `domicile` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `nation` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `religion` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `nationality` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `passport_number` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `permanent_address` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `current_address` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `academic_level` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `qualification` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `ethnic_language` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `language_level` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `job` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `workplace` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `criminal_record` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `arrival_date` date DEFAULT NULL,
    `arrival_reason` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `leave_date` date DEFAULT NULL,
    `leave_reason` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `new_address` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `created_date` date DEFAULT NULL,
    `created_manager_id` INT DEFAULT NULL,
    `deleted_date` date DEFAULT NULL,
    `deleted_manager_id` INT DEFAULT NULL,
    `deleted_reason` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `note` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE `stay`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `person_id` INT DEFAULT NULL,
    `temp_residence_code` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `phone_number` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `from_date` date NOT NULL,
    `to_date` date DEFAULT NULL,
    `reason` text COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE `temp_absent`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `person_id` INT DEFAULT NULL,
    `temp_absent_code` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `temp_residence_place` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `from_date` date DEFAULT NULL,
    `to_date` date DEFAULT NULL,
    `reason` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE `family_member`
(
    `person_id` INT NOT NULL,
    `household_id` INT NOT NULL,
    `host_relation` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    Primary Key (`person_id`, `household_id`)
) ENGINE = InnoDB;

CREATE TABLE `story`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `person_id` INT DEFAULT NULL,
    `from_date` date DEFAULT NULL,
    `to_date` date DEFAULT NULL,
    `address` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `job` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `workplace` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE `users`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `username` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `email` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `keycloak_uid` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

INSERT INTO `users` (ID, username, email) VALUES (1, 'admin', 'wizard201366@gmail.com');

CREATE TABLE `roles`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `name` varchar(100) COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

INSERT INTO `roles`
VALUES (1, 'president'), (2, 'manager'), (3, 'user');

CREATE TABLE `users_roles`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `role_id` INT NOT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

ALTER TABLE `id_card`
    ADD CONSTRAINT `id_card_person_id` FOREIGN KEY (`person_id`) REFERENCES `people` (`ID`);

ALTER TABLE `corrections`
    ADD CONSTRAINT `corrections_household_id` FOREIGN KEY (`household_id`) REFERENCES `household` (`ID`),
    ADD CONSTRAINT `corrections_performer_id` FOREIGN KEY (`performer_id`) REFERENCES `users` (`ID`);

ALTER TABLE `family`
    ADD CONSTRAINT `family_person_id` FOREIGN KEY (`person_id`) REFERENCES `people` (`ID`);

ALTER TABLE `household`
    ADD CONSTRAINT `household_host_id` FOREIGN KEY (`host_id`) REFERENCES `people` (`ID`),
    ADD CONSTRAINT `household_performer_id` FOREIGN KEY (`performer_id`) REFERENCES `users` (`ID`);

ALTER TABLE `death`
    ADD CONSTRAINT `death_declared_person_id` FOREIGN KEY (`declared_person_id`) REFERENCES `people` (`ID`),
    ADD CONSTRAINT `death_death_person_id` FOREIGN KEY (`death_person_id`) REFERENCES `people` (`ID`);

ALTER TABLE `people`
    ADD CONSTRAINT `people_created_manager_id` FOREIGN KEY (`created_manager_id`) REFERENCES `users` (`ID`),
    ADD CONSTRAINT `people_deleted_manager_id` FOREIGN KEY (`deleted_manager_id`) REFERENCES `users` (`ID`);

ALTER TABLE `stay`
    ADD CONSTRAINT `stay_person_id` FOREIGN KEY (`person_id`) REFERENCES `people` (`ID`);

ALTER TABLE `temp_absent`
    ADD CONSTRAINT `temp_absent_person_id` FOREIGN KEY (`person_id`) REFERENCES `people` (`ID`);

ALTER TABLE `family_member`
    ADD CONSTRAINT `family_member_person_id` FOREIGN KEY (`person_id`) REFERENCES `people` (`ID`),
    ADD CONSTRAINT `family_member_household_id` FOREIGN KEY (`household_id`) REFERENCES `household` (`ID`);

ALTER TABLE `story`
    ADD CONSTRAINT `story_person_id` FOREIGN KEY (`person_id`) REFERENCES `people` (`ID`);

ALTER TABLE `users_roles`
    ADD CONSTRAINT `users_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`ID`),
    ADD CONSTRAINT `users_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`ID`);
