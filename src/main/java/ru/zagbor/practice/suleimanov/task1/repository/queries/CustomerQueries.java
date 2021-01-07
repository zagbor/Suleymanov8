package ru.zagbor.practice.suleimanov.task1.repository.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomerQueries {
    GET_CUSTOMER_BY_ID("SELECT * FROM customers WHERE id = %d"),
    CREATE_CUSTOMER("INSERT INTO customers (name, account_id) value ('%s', %d)"),
    IS_EXIST_CUSTOMER("SELECT EXISTS(SELECT id FROM customers WHERE id = %d)"),
    DELETE_CUSTOMER("" +
            "DELETE customers " +
            "FROM customers\n"+
            "WHERE customers.id = %d"),
    UPDATE_CUSTOMER("" +
            "UPDATE customers set\n" +
            "id=%d,\n" +
            "name='%s' where id = %d"),
    GET_ALL_CUSTOMERS("SELECT * FROM customers");

    private final String query;


    public String formatSql(Object... params) {
        return String.format(this.query, params);
    }
}
