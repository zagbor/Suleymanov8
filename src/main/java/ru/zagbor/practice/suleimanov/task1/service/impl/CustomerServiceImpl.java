package ru.zagbor.practice.suleimanov.task1.service.impl;

import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.AccountRepository;
import ru.zagbor.practice.suleimanov.task1.repository.impl.AccountRepositoryImpl;
import ru.zagbor.practice.suleimanov.task1.repository.CustomerRepository;
import ru.zagbor.practice.suleimanov.task1.repository.impl.CustomerRepositoryImpl;
import ru.zagbor.practice.suleimanov.task1.repository.SpecialtyRepository;
import ru.zagbor.practice.suleimanov.task1.repository.impl.SpecialtyRepositoryImpl;
import ru.zagbor.practice.suleimanov.task1.service.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final SpecialtyRepository specialtyRepository;
    private final AccountRepository accountRepository;


    public CustomerServiceImpl() {
        customerRepository = new CustomerRepositoryImpl();
        specialtyRepository = new SpecialtyRepositoryImpl();
        accountRepository = new AccountRepositoryImpl();
    }

    @Override
    public Customer update(Customer customer) {
        accountRepository.update(customer.getAccount());
        customerRepository.update(customer);
        customer.getSpecialties()
                .forEach(specialty ->
                        specialtyRepository.updateSpecialtyInCustomer(customer.getId(), specialty.getId()));
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = customerRepository.getAll();
        customers.forEach(customer -> {
            Set<Specialty> specialties = specialtyRepository.getSpecialtiesCustomer(customer.getId());
            customer.setSpecialties(specialties);
            Account account = accountRepository.getById(customer.getAccount().getId()).orElse(new Account());
            customer.setAccount(account);
        });
        return customers;
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        Customer customer = null;
        if (customerRepository.isCustomerExist(id)) {
            customer = customerRepository.getById(id).orElseThrow(() -> new RuntimeException(format("Customer [%s] not exists", id)));
            Set<Specialty> specialties = specialtyRepository.getSpecialtiesCustomer(id);
            customer.setSpecialties(specialties);
            Account account = accountRepository.getById(customer.getAccount().getId()).orElse(new Account());
            customer.setAccount(account);
        }
        return Optional.of(customer);
    }

    @Override
    public void deleteById(Long id) {
        Customer customer = customerRepository.getById(id).get();
        accountRepository.deleteById(customer.getAccount().getId());
        customerRepository.deleteById(id);
    }

    @Override
    public Customer create(Customer customer) {
        Account account = accountRepository.create(customer.getAccount());
        customer = customerRepository.create(customer);
        specialtyRepository.saveSpecialtiesInCustomers(customer.getId(), customer.getSpecialties());
        customer.setAccount(account);
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
