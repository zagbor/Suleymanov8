package ru.zagbor.practice.suleimanov.task1.repository.impl;

import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.CustomerRepository;
import ru.zagbor.practice.suleimanov.task1.repository.StatementFactory;
import ru.zagbor.practice.suleimanov.task1.utils.Queries;
import ru.zagbor.practice.suleimanov.task1.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;



public class CustomerRepositoryImpl implements CustomerRepository {


    @Override
    public Customer update(Customer customer) {
        Account account = customer.getAccount();
        try (Statement statement = StatementFactory.getPrepareStatement(Queries.UPDATE_ACCOUNT.formatSql(account.getAccountStatus().getId(), account.getId()))) {
            statement.executeUpdate(Queries.UPDATE_ACCOUNT.formatSql(account.getAccountStatus().getId(), account.getId()));
            statement.executeUpdate(Queries.UPDATE_CUSTOMER.formatSql(
                    customer.getId(),
                    customer.getName(),
                    customer.getId()));
            customer.getSpecialties()
                    .forEach(specialty ->
                            updateSpecialtyInCustomer(customer.getId(), specialty.getId(), statement));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    private void updateSpecialtyInCustomer(Long customerId, Long specialtyId, Statement statement) {
        try {
            statement.executeUpdate(Queries.INSERT_SPECIALTY_IN_CUSTOMER.formatSql(customerId, specialtyId));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Customer> getAll() {
        try (Statement statement = StatementFactory.getPrepareStatement(Queries.GET_ALL_CUSTOMERS.getQuery())) {
            List<Customer> customers = new ArrayList<>();
            ResultSet customersSet = statement.executeQuery(Queries.GET_ALL_CUSTOMERS.formatSql());
            while (customersSet.next()) {
                customers.add(CustomerMapper.customer(customersSet));
            }
            return customers;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Customer> getById(Long id) {
        Customer customer = null;
        try (Statement statement = StatementFactory.getPrepareStatement(Queries.GET_CUSTOMER_BY_ID.formatSql(id))) {
            ResultSet resultSet = statement.executeQuery(Queries.GET_CUSTOMER_BY_ID.formatSql(id));
            resultSet.next();
            customer = CustomerMapper.customer(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.of(customer);
    }

    @Override
    public void deleteById(Long id) {
        try (Statement statement = StatementFactory.getPrepareStatement(Queries.DELETE_CUSTOMER.formatSql(id))) {
            statement.executeUpdate(Queries.DELETE_CUSTOMER.formatSql(id));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public Customer create(Customer customer) {
        Account account = customer.getAccount();
        try (Statement statement = StatementFactory.getPrepareStatement(Queries.CREATE_ACCOUNT.formatSql(account.getAccountStatus().getId()))) {
            statement.executeUpdate(Queries.CREATE_ACCOUNT.formatSql(account.getAccountStatus().getId()),
                    Statement.RETURN_GENERATED_KEYS);
            account.setId(Utils.getId(statement));
            statement.executeUpdate(Queries.CREATE_CUSTOMER.formatSql(customer.getName(), account.getId()
            ), Statement.RETURN_GENERATED_KEYS);
            Long customerKey = Utils.getId(statement);
            customer.setId(customerKey);
            customer.getSpecialties().forEach(specialty -> {
                try {
                    statement.executeUpdate(Queries.INSERT_SPECIALTY_IN_CUSTOMER.formatSql(customer.getId(), specialty.getId()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
        return customer;
    }

    @Override
    public void deleteSpecialtyCustomer(Customer customer, Specialty specialty) {
        customer.getSpecialties().remove(specialty);
        this.update(customer);
    }

    @Override
    public boolean isCustomerExist(long id) {
        Statement statement = StatementFactory.getPrepareStatement(Queries.IS_EXIST_CUSTOMER.formatSql(id));
        try {
            ResultSet resultSet = statement.executeQuery(Queries.IS_EXIST_CUSTOMER.formatSql(id));
            resultSet.next();
            if (resultSet.getLong(1) == 1) {
                return true;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return false;
    }


    static class CustomerMapper {

        public static Customer customer(ResultSet resultSet) throws SQLException {
            int accountStatusId = resultSet.getInt("accountstatus_id");
            Account account =
                    Account.builder()
                            .id(resultSet.getLong("account_id"))
                            .accountStatus(Account.AccountStatus.fromId(accountStatusId))
                            .build();
            Customer customer = Customer.builder()
                    .id(resultSet.getLong("customer_id"))
                    .name(resultSet.getString("customer_name"))
                    .account(account)
                    .build();
            Set<Specialty> specialties = new HashSet<>();


            long customer_id;
            do {
                customer_id = resultSet.getLong("customer_id");
                if (customer_id == customer.getId()) {
                    specialties.add(Specialty.builder()
                            .id(resultSet.getLong("specialty_id"))
                            .name(resultSet.getString("specialty_name"))
                            .build());
                } else {
                    resultSet.previous();
                    break;
                }
            } while (resultSet.next());

            customer.setSpecialties(specialties);
            return customer;
        }

    }
}
