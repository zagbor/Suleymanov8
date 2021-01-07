package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.util.Set;

public interface SpecialtyRepository extends GenericRepository<Specialty, Long> {

    void deleteSpecialtyCustomer(Long specialtyId, Long customerId);

    boolean isSpecialtyExist(long id);

    Set<Specialty> getAll();

    Set<Specialty> getSpecialtiesCustomer(Long id);

    void saveSpecialtiesInCustomers(Long id, Set<Specialty> specialties);

    void updateSpecialtyInCustomer(Long customerId, Long specialtyId);


}
