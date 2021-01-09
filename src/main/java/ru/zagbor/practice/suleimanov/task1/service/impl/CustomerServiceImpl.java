package ru.zagbor.practice.suleimanov.task1.service.impl;

import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.CustomerRepository;
import ru.zagbor.practice.suleimanov.task1.repository.impl.CustomerRepositoryImpl;
import ru.zagbor.practice.suleimanov.task1.service.CustomerService;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl() {
        this(new CustomerRepositoryImpl());
    }

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }


    @Override
    public Customer update(Customer customer) {
        customerRepository.update(customer);
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = customerRepository.getAll();
        return customers;
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        Customer customer = null;
        if (customerRepository.isCustomerExist(id)) {
            customer = customerRepository.getById(id).orElseThrow(() -> new RuntimeException(format("Customer [%s] not exists", id)));
        }
        return Optional.of(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer create(Customer customer) {
        customerRepository.create(customer);
        return customer;
    }

    @Override
    public void deleteSpecialtyCustomer(Customer customer, Specialty specialty) {
        customer.getSpecialties().remove(specialty);
        customerRepository.update(customer);
    }

    @Override
    public void changeName(Customer customer, String name) {
        customer.setName(name);
        customerRepository.update(customer);
    }


    @Override
    public boolean isCustomerExist(long id) {
        return customerRepository.isCustomerExist(id);
    }
}
