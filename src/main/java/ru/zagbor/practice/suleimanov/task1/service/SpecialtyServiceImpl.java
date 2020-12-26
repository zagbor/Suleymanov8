package ru.zagbor.practice.suleimanov.task1.service;

import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.SpecialtyRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }


    public SpecialtyServiceImpl() {
        this(new ru.zagbor.practice.suleimanov.task1.repository.SpecialtyServiceImpl());
    }


    @Override
    public Set<Specialty> getAll() throws IOException, SQLException {
        return specialtyRepository.getAll();
    }

    @Override
    public Optional<Specialty> getById(Long id) throws IOException, SQLException {
        return specialtyRepository.getById(id);
    }

    @Override
    public void deleteById(Long id) throws IOException, SQLException {
        specialtyRepository.deleteById(id);
    }

    @Override
    public Specialty create(Specialty specialty) throws IOException, SQLException {
        return specialtyRepository.create(specialty);
    }


    @Override
    public boolean isSpecialtyExist(long id) throws IOException, SQLException {
        return specialtyRepository.isSpecialtyExist(id);
    }


    @Override
    public Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException, SQLException {
        return specialtyRepository.findWhichCanAdd(specialtiesCustomer);
    }
}
