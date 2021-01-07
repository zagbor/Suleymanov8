package ru.zagbor.practice.suleimanov.task1.service;

import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer update(Customer customer);

    List<Customer> getAll();

    Optional<Customer> getCustomerById(Long id);

    void deleteById(Long id);

    Customer create(Customer customer);

    void deleteSpecialtyCustomer(Customer customer, Specialty specialty);

    void changeName(Customer customer, String name);

    boolean isCustomerExist(long id);
}
