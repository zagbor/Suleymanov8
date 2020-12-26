  create TABLE `suleimanov`.`customers_specialties` (
  `customer_id` INT NOT NULL,
  `specialty_id` INT NOT NULL,
   CONSTRAINT  FOREIGN KEY (customer_id) REFERENCES customers(id) ON delete CASCADE  ON update CASCADE,
   CONSTRAINT  FOREIGN KEY (specialty_id) REFERENCES specialties(id) ON delete CASCADE  ON update CASCADE
)ENGINE=InnoDB;