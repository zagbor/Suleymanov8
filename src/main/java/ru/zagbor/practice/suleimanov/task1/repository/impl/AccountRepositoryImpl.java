package ru.zagbor.practice.suleimanov.task1.repository.impl;


import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.repository.StatementFactory;
import ru.zagbor.practice.suleimanov.task1.repository.AccountRepository;
import ru.zagbor.practice.suleimanov.task1.utils.Queries;
import ru.zagbor.practice.suleimanov.task1.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {

    public AccountRepositoryImpl() {
    }

    public void deleteAll() {

    }

    @Override
    public Optional<Account> getById(Long id) {
        Statement statement = StatementFactory.getStatement();
        Account account = null;
        try {
            ResultSet resultSet = statement.executeQuery(Queries.GET_ACCOUNT_BY_ID.formatSql(id));
            resultSet.next();
            account = AccountMapper.account(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.of(account);
    }

    @Override
    public Account update(Account account) {
        try (Statement statement = StatementFactory.getStatement()) {
            statement.executeUpdate(Queries.UPDATE_ACCOUNT.formatSql(account.getAccountStatus().getId(), account.getId()));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return account;
    }

    @Override
    public void deleteById(Long id) {
        Statement statement = StatementFactory.getStatement();
        try {
            statement.executeUpdate(Queries.DELETE_ACCOUNT.formatSql(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> getAll() {/*
        Type itemsListType = new TypeToken<List<Account>>() {
        }.getType();

        String data = new String(Files.readAllBytes(Paths.get(ACCOUNTS)), StandardCharsets.UTF_8);
        if (data.isEmpty()) {
            return new ArrayList<Account>();
        }
        return new Gson().fromJson(data, itemsListType);*/
        return null;
    }

    @Override
    public Account create(Account account) {
        try (Statement statement = StatementFactory.getStatement()) {
            statement.executeUpdate(Queries.CREATE_ACCOUNT.
                    formatSql(account.getAccountStatus().getId()), Statement.RETURN_GENERATED_KEYS);
            account.setId(Utils.getId(statement));
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Account();
    }

    public void saveAccountStatus(Long idCustomer, Integer idAccountStatus) {
        try (Statement statement = StatementFactory.getStatement()) {
            statement.executeQuery(Queries.INSERT_ACCOUNT_STATUS_IN_CUSTOMER.formatSql(idCustomer, idAccountStatus));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    private static class AccountMapper {
        public static Account account(ResultSet resultSet) throws SQLException {
            int accountStatusId = resultSet.getInt("accountstatus_id");
            return Account.builder()
                    .id(resultSet.getLong("id"))
                    .accountStatus(Account.AccountStatus.fromId(accountStatusId))
                    .build();
        }
    }


}
