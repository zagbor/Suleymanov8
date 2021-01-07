package ru.zagbor.practice.suleimanov.task1.controller;

import ru.zagbor.practice.suleimanov.task1.service.CustomerService;
import ru.zagbor.practice.suleimanov.task1.service.impl.CustomerServiceImpl;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.util.List;
import java.util.Optional;


public class CustomerController{

    private final CustomerService customerService = new CustomerServiceImpl();

    public CustomerController() {
    }


    public void create(Customer customer) {
        customerService.create(customer);
    }


    public List<Customer> getAll()  {
        return customerService.getAll();
    }


    public Optional<Customer> getCustomerForID(long id) {
        return customerService.getCustomerById(id);

    }
    public boolean isCustomerExist(long id) {
        return customerService.isCustomerExist(id);
    }


    public void deleteCustomerForID(long id) {
        customerService.deleteById(id);
    }


    public void changeName(Customer customer, String name) {
        customerService.changeName(customer, name);
    }

    public void deleteSpecialtyCustomer(Customer customer, Specialty specialty) {
        customerService.deleteSpecialtyCustomer(customer, specialty);
    }
}


