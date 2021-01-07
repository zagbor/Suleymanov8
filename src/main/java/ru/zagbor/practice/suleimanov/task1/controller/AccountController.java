package ru.zagbor.practice.suleimanov.task1.controller;

import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.service.AccountService;
import ru.zagbor.practice.suleimanov.task1.service.impl.AccountServiceImpl;

public class AccountController {

    private final AccountService accountService = new AccountServiceImpl();

    public AccountController() {
    }


    public void changeAccountStatus(Account account, Account.AccountStatus accountStatus) {
        accountService.changeAccountStatus(account, accountStatus);
    }

}
