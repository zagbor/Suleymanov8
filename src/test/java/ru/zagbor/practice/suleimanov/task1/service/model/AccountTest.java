package ru.zagbor.practice.suleimanov.task1.service.model;

import org.junit.Test;
import ru.zagbor.practice.suleimanov.task1.model.Account;

public class AccountTest {
    @Test
    public void testFromId() {
        Account.AccountStatus accountStatus = Account.AccountStatus.fromId(2);
        System.out.println(accountStatus);
    }
}
