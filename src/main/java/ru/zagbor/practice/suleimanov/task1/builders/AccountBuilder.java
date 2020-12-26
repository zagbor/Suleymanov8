package ru.zagbor.practice.suleimanov.task1.builders;

import ru.zagbor.practice.suleimanov.task1.model.Account;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountBuilder {


    public Account builder(CachedRowSet cachedRowSet) throws SQLException {
        Account account = new Account();
        account.setId(cachedRowSet.getInt("id"));
        account.setAccountStatus(Account.AccountStatus.valueOf(cachedRowSet.getString("name")));
        return account;
    }

    public Map<Long, Account> builderMapIdCustomersAccount(CachedRowSet cachedRowSet) throws SQLException {
        Map<Long, Account> longAccountMap = new HashMap<>();
        while (cachedRowSet.next()) {
            Account account = builder(cachedRowSet);
            Long key = cachedRowSet.getLong("id");
            longAccountMap.put(key, account);
        }
        return longAccountMap;
    }

    public Long idParser(CachedRowSet cachedRowSet) throws SQLException {
        Long l = null;
        while (cachedRowSet.next()) {
            l = cachedRowSet.getLong("id");
        }
        return l;
    }

    public List<Account> builderListAccounts(CachedRowSet cachedRowSet) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        while (cachedRowSet.next()) {
            accounts.add(builder(cachedRowSet));
        }
        return accounts;
    }


}
