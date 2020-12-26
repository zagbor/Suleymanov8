
  CREATE TABLE `suleimanov`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(245) NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`id`),
   CONSTRAINT  FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
   ON UPDATE CASCADE
  )ENGINE=InnoDB;