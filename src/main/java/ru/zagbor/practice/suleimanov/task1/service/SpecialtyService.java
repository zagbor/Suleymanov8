package ru.zagbor.practice.suleimanov.task1.service;

import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.util.Optional;
import java.util.Set;

public interface SpecialtyService {

    Set<Specialty> getAll();

    Optional<Specialty> getById(Long id);

    void deleteById(Long id);

    Specialty create(Specialty specialty);

    boolean isSpecialtyExist(long id);

    void addSpecialtyCustomer(Customer customer, Long specialtyId);

    void deleteSpecialtyCustomer(Long specialtyId, Long customerId);

    Set<Specialty> findWhichCanAdd(Set<Specialty> specialties);

}
