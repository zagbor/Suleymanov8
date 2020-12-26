package ru.zagbor.practice.suleimanov.task1.controller;

import ru.zagbor.practice.suleimanov.task1.service.CustomerService;
import ru.zagbor.practice.suleimanov.task1.service.CustomerServiceImpl;
import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class CustomerControllerImpl implements CustomerController {

    private final CustomerService customerService = new CustomerServiceImpl();

    public CustomerControllerImpl() throws IOException {
    }

    @Override
    public void create(Customer customer) throws IOException, SQLException {
        customerService.create(customer);
    }

    @Override
    public List<Customer> getAll() throws IOException, SQLException {
        return customerService.getAll();
    }

    @Override
    public Optional<Customer> getCustomerForID(long id) throws IOException, SQLException {
        return customerService.getById(id);

    }

    @Override
    public void addSpecialtyCustomer(long customerId, Specialty specialty) throws IOException, SQLException {
        customerService.addSpecialtyCustomer(customerId, specialty);
    }

    @Override
    public boolean isCustomerExist(long id) throws IOException, SQLException {
        return customerService.isCustomerExist(id);
    }

    @Override
    public void deleteCustomerForID(long id) throws IOException, SQLException {
        customerService.deleteById(id);
    }

    @Override
    public void changeName(Customer customer, String name) throws IOException, SQLException {
        customerService.changeName(customer, name);
    }

    @Override
    public void changeAccountStatus(Customer customer, Account.AccountStatus status) throws IOException, SQLException {
        customerService.changeAccountStatus(customer, status);
    }

    @Override
    public void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException, SQLException {
        customerService.deleteSpecialtyCustomer(customer, specialty);
    }
}


