use qldp;

CREATE TABLE `household_histories`
(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `household_id` INT DEFAULT NULL,
    `event` varchar(100) COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `affect_person_id` INT DEFAULT NULL,
    `new_household_id` INT DEFAULT NULL,
    `date` DATE DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

ALTER TABLE `household_histories`
    ADD CONSTRAINT `household_histories_household_id` FOREIGN KEY (`household_id`) REFERENCES `household` (`ID`),
    ADD CONSTRAINT `household_histories_affect_person_id` FOREIGN KEY (`affect_person_id`) REFERENCES `people` (`ID`),
    ADD CONSTRAINT `household_histories_new_household_id` FOREIGN KEY (`new_household_id`) REFERENCES `household` (`ID`);
