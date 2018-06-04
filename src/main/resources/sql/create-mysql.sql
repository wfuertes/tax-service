CREATE SCHEMA IF NOT EXISTS `tax_service_db`;

DROP TABLE IF EXISTS `tax_service_db`.`taxes`;

CREATE TABLE IF NOT EXISTS `tax_service_db`.`taxes` (
  `id` VARCHAR(32) NOT NULL,
  `value` DECIMAL(14,6) NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- Initial data
INSERT INTO `tax_service_db`.`taxes` values ('ICMS', 0.17, CURRENT_TIMESTAMP());
INSERT INTO `tax_service_db`.`taxes` values ('ISS', 0.07, CURRENT_TIMESTAMP());
INSERT INTO `tax_service_db`.`taxes` values ('ITBI', 0.04, CURRENT_TIMESTAMP());
INSERT INTO `tax_service_db`.`taxes` values ('PIS', 0.01, CURRENT_TIMESTAMP());
INSERT INTO `tax_service_db`.`taxes` values ('COFINS', 0.01, CURRENT_TIMESTAMP());
