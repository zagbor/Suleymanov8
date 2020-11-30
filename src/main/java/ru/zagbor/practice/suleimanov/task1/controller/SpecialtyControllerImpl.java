package ru.zagbor.practice.suleimanov.task1.controller;


import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.SpecialtyRepository;
import ru.zagbor.practice.suleimanov.task1.repository.SpecialtyRepositoryImpl;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class SpecialtyControllerImpl implements SpecialtyController {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyControllerImpl() throws IOException {
        specialtyRepository = new SpecialtyRepositoryImpl();
    }

    @Override
    public Optional<Specialty> getSpecialtyForId(long id) throws IOException {
        return specialtyRepository.getById(id);
    }

    @Override
    public Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException {
        return specialtyRepository.findWhichCanAdd(specialtiesCustomer);
    }

    @Override
    public boolean isSpecialtyExist(long id) throws IOException {
        return specialtyRepository.isSpecialtyExist(id);
    }

    @Override
    public Specialty create(Specialty specialty) throws IOException {
        return specialtyRepository.create(specialty);
    }


    @Override
    public Set<Specialty> getAll() throws IOException {
        return specialtyRepository.getAll();
    }


    public void deleteById(Long id) throws IOException {
        specialtyRepository.deleteById(id);
    }
}