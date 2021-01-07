package ru.zagbor.practice.suleimanov.task1.service;

import ru.zagbor.practice.suleimanov.task1.model.Account;

public interface AccountService {
    void changeAccountStatus(Account account, Account.AccountStatus accountStatus);
}
