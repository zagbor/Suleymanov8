package ru.zagbor.practice.suleimanov.task1.controller;


import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CustomerController {

    void create(Customer customer) throws IOException, SQLException;

    List<Customer> getAll() throws IOException, SQLException;

    Optional<Customer> getCustomerForID(long id) throws IOException, SQLException;

    boolean isCustomerExist(long id) throws IOException, SQLException;

    void deleteCustomerForID(long id) throws IOException, SQLException;

    void changeName(Customer customer, String name) throws IOException, SQLException;

    void addSpecialtyCustomer(long customerId, Specialty specialty) throws IOException, SQLException;

    void changeAccountStatus(Customer customer, Account.AccountStatus accountStatus) throws IOException, SQLException;

    void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException, SQLException;


}
