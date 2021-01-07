package ru.zagbor.practice.suleimanov.task1.service.impl;

import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.SpecialtyRepository;
import ru.zagbor.practice.suleimanov.task1.repository.impl.SpecialtyRepositoryImpl;
import ru.zagbor.practice.suleimanov.task1.service.SpecialtyService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }


    public SpecialtyServiceImpl() {
        this(new SpecialtyRepositoryImpl());
    }


    @Override
    public Set<Specialty> getAll() {
        return specialtyRepository.getAll();
    }

    @Override
    public Optional<Specialty> getById(Long id) {
        return specialtyRepository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        specialtyRepository.deleteById(id);
    }

    @Override
    public Specialty create(Specialty specialty) {
        return specialtyRepository.create(specialty);
    }


    @Override
    public boolean isSpecialtyExist(long id) {
        return specialtyRepository.isSpecialtyExist(id);
    }


    @Override
    public Set<Specialty> findWhichCanAdd(Set<Specialty> specialties) {
        Set<Specialty> allSpecialties = specialtyRepository.getAll();
        return allSpecialties.stream().filter(specialty -> !specialties.contains(specialty)).collect(Collectors.toSet());
    }

    @Override
    public void addSpecialtyCustomer(Customer customer, Long specialtyId) {
        Specialty specialty = specialtyRepository.getById(specialtyId).orElseThrow(IllegalStateException::new);
        customer.getSpecialties().add(specialty);
        specialtyRepository.saveSpecialtiesInCustomers(customer.getId(), customer.getSpecialties());
    }

    @Override
    public void deleteSpecialtyCustomer(Long specialtyId, Long customerId) {
        specialtyRepository.deleteSpecialtyCustomer(specialtyId, customerId);
    }
}
