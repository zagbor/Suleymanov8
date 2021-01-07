package ru.zagbor.practice.suleimanov.task1.repository;


import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.util.List;

public interface CustomerRepository extends GenericRepository<Customer, Long> {

    List<Customer> getAll();

    void deleteSpecialtyCustomer(Customer customer, Specialty specialty) ;

    boolean isCustomerExist(long id);

}
