package ru.zagbor.practice.suleimanov.task1.model;

import java.util.Arrays;

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

    public Account(long id, AccountStatus accountStatus) {
        this.id = id;
        this.accountStatus = accountStatus;
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
            return Arrays.stream(AccountStatus.values()).findFirst().filter(accountStatus1 -> accountStatus1.getId() == id).get();
        }

        @Override
        public String toString() {
            return "AccountStatus{" +
                    "id=" + id +
                    '}';
        }
    }
}
