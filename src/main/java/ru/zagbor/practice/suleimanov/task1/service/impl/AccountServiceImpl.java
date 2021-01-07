package ru.zagbor.practice.suleimanov.task1.service.impl;

import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.repository.AccountRepository;
import ru.zagbor.practice.suleimanov.task1.repository.impl.AccountRepositoryImpl;
import ru.zagbor.practice.suleimanov.task1.service.AccountService;

public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository = new AccountRepositoryImpl();

    @Override
    public void changeAccountStatus(Account account, Account.AccountStatus accountStatus) {
        account.setAccountStatus(accountStatus);
        accountRepository.update(account);
    }
}
