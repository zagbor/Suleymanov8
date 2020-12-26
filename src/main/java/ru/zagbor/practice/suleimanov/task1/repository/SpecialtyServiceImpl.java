package ru.zagbor.practice.suleimanov.task1.repository;


import org.junit.Assert;
import org.junit.Test;
import ru.zagbor.practice.suleimanov.task1.builders.SpecialtyBuilder;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SpecialtyServiceImpl implements SpecialtyRepository {

    private final CrudRepository crudRepository;


    public SpecialtyServiceImpl() {
        crudRepository = new CrudRepositoryImpl();
    }

    @Override
    public Optional<Specialty> getById(Long id) throws IOException, SQLException {
        Transaction transaction = new TransactionImpl();
        Statement statement = transaction.createTransaction();
        CachedRowSet cachedRowSet = crudRepository.selectForId(statement, "specialties", "id", Long.toString(id));
        Set<Specialty> specialties = new SpecialtyBuilder().builderListSpecialty(cachedRowSet);
        if (specialties.size() > 0) {
            Specialty specialty = specialties.stream().findFirst().get();
            return Optional.of(specialty);
        }
        return Optional.empty();
    }

    @Override
    public Specialty update(Specialty specialty) throws IOException, SQLException {
        Transaction transaction = new TransactionImpl();
        Statement statement = transaction.createTransaction();
        crudRepository.executeUpdate(statement, "DELETE specialties from " +
                "specialties where id=" + specialty.getId());
        if (specialty.getId() == 0) {
            specialty.setId(crudRepository.findMaxId(statement, "specialties", "id") + 1);
        }
        crudRepository.insert(statement, "specialties", List.of("id", "name"),
                List.of(Long.toString(specialty.getId()), specialty.getName()));
        transaction.closeTransaction();
        return specialty;
    }

    @Override
    public Specialty create(Specialty specialty) throws IOException, SQLException {
        Transaction transaction = new TransactionImpl();
        Statement statement = transaction.createTransaction();
        if (specialty.getId() == 0) {
            specialty.setId(crudRepository.findMaxId(statement, "specialties", "id") + 1);
        }
        crudRepository.insert(statement, "specialties", List.of("id", "name"),
                List.of(Long.toString(specialty.getId()), specialty.getName()));
        transaction.closeTransaction();
        return specialty;
    }


    @Override
    public void deleteById(Long id) throws IOException, SQLException {
        Transaction transaction = new TransactionImpl();
        Statement statement = transaction.createTransaction();
        crudRepository.executeUpdate(statement, "DELETE specialties from " +
                "specialties where id=" + id);
        transaction.closeTransaction();
    }


    @Override
    public String specialtiesToStringForBase(Set<Specialty> specialties) {
        if (specialties == null) {
            return "";
        }
        return specialties.stream()
                .map(s -> String.valueOf(s.getId()))
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Specialty> findWhichCanAdd(Set<Specialty> specialtiesCustomer) throws IOException, SQLException {
        Set<Specialty> allSpecialties = getAll();
        return allSpecialties.stream()
                .filter(specialty -> !specialtiesCustomer.contains(specialty))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isSpecialtyExist(long id) throws IOException, SQLException {
        Optional<Specialty> specialty = getById(id);
        return specialty.isPresent();
    }


    @Override
    public Set<Specialty> getAll() throws IOException, SQLException {
        Transaction transaction = new TransactionImpl();
        Statement statement = transaction.createTransaction();
        CachedRowSet cachedRowSetSpecialties = getAllModelCustomers(statement);
        transaction.closeTransaction();
        return new SpecialtyBuilder().builderListSpecialty(cachedRowSetSpecialties);

    }

    private CachedRowSet getAllModelCustomers(Statement statement) throws SQLException {
        return crudRepository.executeQuery(statement, "SELECT * from specialties");
    }

    @Test
    public void isSpecialtyExist() throws IOException, SQLException {
        boolean isExist;
        long id = 10;
        Optional<Specialty> specialty = getById(id);
        isExist = specialty.isPresent();
        Assert.assertFalse(isExist);
    }


}
