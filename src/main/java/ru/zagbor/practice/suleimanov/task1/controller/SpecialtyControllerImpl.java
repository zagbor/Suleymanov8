package ru.zagbor.practice.suleimanov.task1.controller;


import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.service.SpecialtyService;
import ru.zagbor.practice.suleimanov.task1.service.SpecialtyServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class SpecialtyControllerImpl implements SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyControllerImpl() throws IOException {
        specialtyService = new SpecialtyServiceImpl();
    }

    @Override
    public Optional<Specialty> getSpecialtyForId(long id) throws IOException, SQLException {
        return specialtyService.getById(id);
    }

    @Override
    public Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException, SQLException {
        return specialtyService.findWhichCanAdd(specialtiesCustomer);
    }

    @Override
    public boolean isSpecialtyExist(long id) throws IOException, SQLException {
        return specialtyService.isSpecialtyExist(id);
    }

    @Override
    public Specialty create(Specialty specialty) throws IOException, SQLException {
        return specialtyService.create(specialty);
    }


    @Override
    public Set<Specialty> getAll() throws IOException, SQLException {
        return specialtyService.getAll();
    }


    public void deleteById(Long id) throws IOException, SQLException {
        specialtyService.deleteById(id);
    }
}