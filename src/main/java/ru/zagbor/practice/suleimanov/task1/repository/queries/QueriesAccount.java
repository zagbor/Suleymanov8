package ru.zagbor.practice.suleimanov.task1.repository.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QueriesAccount {

    CREATE_ACCOUNT("INSERT INTO accounts (accountstatus_id) value (%d)"),
    UPDATE_ACCOUNT("UPDATE accounts SET  accountstatus_id = %d WHERE id = %d"),
    DELETE_ACCOUNT("DELETE accounts FROM accounts where accounts.id = %d"),
    GET_ALL_ACCOUNTS("SELECT * FROM accounts"),
    GET_ALL_CUSTOMERS_ACCOUNTS("SELECT  c.id as customer_id,\n" +
            "a.id as account_id ,ac.name as accountstatus  from customers c\n" +
            "join accounts a on c.account_id=a.id\n" +
            "join accountstatuses ac on a.accountstatus_id=ac.id"),

    GET_ACCOUNT_BY_ID("SELECT * FROM accounts WHERE id = %d"),
    INSERT_ACCOUNT_STATUS_IN_CUSTOMER("INSERT INTO accounts (accountstatus_id) value (%d)");


    private final String query;

    public String formatSql(Object... params) {
        return String.format(this.query, params);
    }
}
