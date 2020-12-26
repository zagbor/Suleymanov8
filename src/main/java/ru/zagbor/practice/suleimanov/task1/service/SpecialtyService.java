package ru.zagbor.practice.suleimanov.task1.service;

import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface SpecialtyService {

    Set<Specialty> getAll() throws IOException, SQLException;

    Optional<Specialty> getById(Long id) throws IOException, SQLException;

    void deleteById(Long id) throws IOException, SQLException;

    Specialty create(Specialty specialty) throws IOException, SQLException;

    boolean isSpecialtyExist(long id) throws IOException, SQLException;

    Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException, SQLException;
}