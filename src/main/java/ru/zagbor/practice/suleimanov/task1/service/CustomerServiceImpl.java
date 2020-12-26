package ru.zagbor.practice.suleimanov.task1.service;

import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.CustomerRepository;
import ru.zagbor.practice.suleimanov.task1.repository.CustomerRepositoryImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl() {
        this(new CustomerRepositoryImpl());
    }

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<Customer> getAll() throws IOException, SQLException {
        return customerRepository.getAll();
    }
    @Override
    public Optional<Customer> getById(Long id) throws IOException, SQLException {
        return customerRepository.getById(id);
    }
    @Override
    public void deleteById(Long id) throws IOException, SQLException {
        customerRepository.deleteById(id);
    }
    @Override
    public Customer create(Customer customer) throws IOException, SQLException {
        return customerRepository.create(customer);
    }

    @Override
    public void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException, SQLException {
        customer.getSpecialties().remove(specialty);
        customerRepository.update(customer);
    }

    @Override
    public void changeName(Customer customer, String name) throws IOException, SQLException {
       customer.setName(name);
        customerRepository.update(customer);
    }

    @Override
    public void changeAccountStatus(Customer customer, Account.AccountStatus accountStatus) throws IOException, SQLException {
        customer.getAccount().setAccountStatus(accountStatus);
        customerRepository.update(customer);
    }
    @Override
    public void addSpecialtyCustomer(long customerId, Specialty specialty) throws IOException, SQLException {
        Customer customer = getById(customerId).get();
        customer.getSpecialties().add(specialty);
        customerRepository.update(customer);
    }
    @Override
    public boolean isCustomerExist(long id) throws IOException, SQLException {
      return customerRepository.isCustomerExist(id);
    }
}
