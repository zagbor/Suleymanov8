package ru.zagbor.practice.suleimanov.task1.repository.impl;

import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.StatementFactory;
import ru.zagbor.practice.suleimanov.task1.repository.SpecialtyRepository;
import ru.zagbor.practice.suleimanov.task1.repository.queries.SpecialtyQueries;
import ru.zagbor.practice.suleimanov.task1.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class SpecialtyRepositoryImpl implements SpecialtyRepository {


    public SpecialtyRepositoryImpl() {
    }

    @Override
    public Optional<Specialty> getById(Long id) {
        Statement statement = StatementFactory.getStatement();
        Specialty specialty;
        try {
            ResultSet resultSet = statement.executeQuery(SpecialtyQueries.GET_SPECIALTY_BY_ID.formatSql(id));
            resultSet.next();
            specialty = SpecialtyMapper.specialty(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.of(specialty);
    }

    @Override
    public Set<Specialty> getSpecialtiesCustomer(Long id) {
        Set<Specialty> specialties = new HashSet<>();
        try (Statement statement = StatementFactory.getStatement()) {
            ResultSet resultSet = statement.executeQuery(SpecialtyQueries.GET_SET_SPECIALTIES_CUSTOMER.formatSql(id));
            while (resultSet.next()) {
                Specialty specialty = new Specialty();
                specialty.setId(resultSet.getLong("id"));
                specialty.setName(resultSet.getString("name"));
                specialties.add(specialty);
            }
            return specialties;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void updateSpecialtyInCustomer(Long customerId, Long specialtyId) {
        Statement statement = StatementFactory.getStatement();
        try {
            statement.executeUpdate(SpecialtyQueries.INSERT_SPECIALTY_IN_CUSTOMER.formatSql(customerId, specialtyId));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Specialty update(Specialty specialty) {
        Statement statement = StatementFactory.getStatement();
        try {
            statement.executeUpdate(
                    SpecialtyQueries.DELETE_SPECIALTY.formatSql(specialty.getId()));
            statement.executeUpdate(
                    SpecialtyQueries.INSERT_SPECIALTY.formatSql(specialty.getId(), specialty.getName()));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return specialty;
    }

    @Override
    public Specialty create(Specialty specialty) {
        Statement statement = StatementFactory.getStatement();
        try {
            statement.executeUpdate(
                    SpecialtyQueries.CREATE_SPECIALTY.formatSql(specialty.getName()),
                    Statement.RETURN_GENERATED_KEYS);
            specialty.setId(Utils.getId(statement));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return specialty;
    }


    @Override
    public void deleteById(Long id) {
        Statement statement = StatementFactory.getStatement();
        try {
            statement.executeUpdate(SpecialtyQueries.DELETE_SPECIALTY.formatSql(id));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public boolean isSpecialtyExist(long id) {
        try (Statement statement = StatementFactory.getStatement()) {
            System.out.println(SpecialtyQueries.IS_EXIST_SPECIALTY.formatSql(id));
            ResultSet resultSet = statement.executeQuery(SpecialtyQueries.IS_EXIST_SPECIALTY.formatSql(id));
            resultSet.next();
            if (resultSet.getLong(1) == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Set<Specialty> getAll() {
        Statement statement = StatementFactory.getStatement();
        Set<Specialty> specialties = new HashSet<>();
        try {
            ResultSet resultSet = statement.executeQuery(SpecialtyQueries.GET_ALL_SPECIALTIES.getQuery());
            while (resultSet.next()) {
                Specialty specialty = SpecialtyMapper.specialty(resultSet);
                specialties.add(specialty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialties;
    }

    @Override
    public void saveSpecialtiesInCustomers(Long customerId, Set<Specialty> specialties) {
        try (Statement statement = StatementFactory.getStatement()) {
            specialties.forEach(specialty -> {
                try {
                    statement.executeUpdate(SpecialtyQueries.INSERT_SPECIALTY_IN_CUSTOMER.formatSql(customerId, specialty.getId()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            throw new IllegalStateException();
        }

    }



    @Override
    public void deleteSpecialtyCustomer(Long specialtyId, Long customerId) {
        try (Statement statement = StatementFactory.getStatement()) {
            statement.executeUpdate(SpecialtyQueries.DELETE_SPECIALTY_FROM_CUSTOMER.formatSql(customerId, specialtyId));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    static class SpecialtyMapper {

        public static Specialty specialty(ResultSet resultSet) throws SQLException {
            return Specialty.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .build();
        }

    }
    private boolean isSpecialtyExistInCustomer(Long specialtyId, Long customerId) {
        try (Statement statement = StatementFactory.getStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    SpecialtyQueries.IS_EXIST_SPECIALTY_IN_CUSTOMER.formatSql(customerId, specialtyId));
            resultSet.next();
            if (resultSet.getLong(1) == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
