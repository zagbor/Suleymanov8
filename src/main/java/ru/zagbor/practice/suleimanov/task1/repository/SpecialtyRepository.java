package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public interface SpecialtyRepository extends GenericRepository<Specialty, Long> {


    String specialtiesToStringForBase(Set<Specialty> specialties);

    Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException, SQLException;

    boolean isSpecialtyExist(long id) throws IOException, SQLException;

    Set<Specialty> getAll() throws IOException, SQLException;

}
