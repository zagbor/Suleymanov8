package ru.zagbor.practice.suleimanov.task1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
public class Account {
    private long id;
    private AccountStatus accountStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account() {
    }


    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public enum AccountStatus {

        ACTIVE(1),
        BANNED(2),
        DELETED(3);

        public int getId() {
            return id;
        }

        private int id;

        AccountStatus(int id) {
            this.id = id;
        }

        public static AccountStatus fromId(int id) {
            return Arrays.stream(AccountStatus.values()).filter(accountStatus1 ->
                    accountStatus1.getId() == id).findFirst().orElse(null);
        }


    }
}
