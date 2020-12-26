package ru.zagbor.practice.suleimanov.task1.repository;


import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CustomerRepository extends GenericRepository<Customer, Long> {
    List<Customer> getAll() throws IOException, SQLException;

    void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException, SQLException;

    boolean isCustomerExist(long id) throws IOException, SQLException;

}
