package ru.zagbor.practice.suleimanov.task1.repository.impl;

import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;
import ru.zagbor.practice.suleimanov.task1.repository.CustomerRepository;
import ru.zagbor.practice.suleimanov.task1.repository.StatementFactory;
import ru.zagbor.practice.suleimanov.task1.repository.queries.CustomerQueries;
import ru.zagbor.practice.suleimanov.task1.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl implements CustomerRepository {



    @Override
    public Customer update(Customer customer) {
        try (Statement statement = StatementFactory.getStatement()) {
            statement.executeUpdate(CustomerQueries.UPDATE_CUSTOMER.formatSql(
                    customer.getId(),
                    customer.getName(),
                    customer.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }


    @Override
    public List<Customer> getAll() {
        try (Statement statement = StatementFactory.getStatement()) {
            List<Customer> customers = new ArrayList<>();
            ResultSet customersSet = statement.executeQuery(CustomerQueries.GET_ALL_CUSTOMERS.getQuery());
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
        try (Statement statement = StatementFactory.getStatement()) {
            ResultSet resultSet = statement.executeQuery(CustomerQueries.GET_CUSTOMER_BY_ID.formatSql(id));
            resultSet.next();
            customer = CustomerMapper.customer(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.of(customer);
    }

    @Override
    public void deleteById(Long id) {
        try (Statement statement = StatementFactory.getStatement()) {
            statement.executeUpdate(CustomerQueries.DELETE_CUSTOMER.formatSql(id));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public Customer create(Customer customer) {
        try (Statement statement = StatementFactory.getStatement()) {
            statement.executeUpdate(CustomerQueries.CREATE_CUSTOMER.formatSql(
                    customer.getName(), customer.getAccount().getId()), Statement.RETURN_GENERATED_KEYS);
            Long customerKey = Utils.getId(statement);
            customer.setId(customerKey);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
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
        Statement statement = StatementFactory.getStatement();
        try {
            ResultSet resultSet = statement.executeQuery(CustomerQueries.IS_EXIST_CUSTOMER.formatSql(id));
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
            Account account = new Account();
            account.setId(resultSet.getLong("account_id"));
            Customer.CustomerBuilder builder = Customer.builder();
            builder.id(resultSet.getLong("id"));
            builder.name(resultSet.getString("name"));
            builder.account(account);
            return builder
                    .build();
        }
    }
}
