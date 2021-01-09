package ru.zagbor.practice.suleimanov.task1.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Queries {
    GET_CUSTOMER_BY_ID("SELECT a.id as account_id,  acs.id as accountstatus_id, c.id as customer_id, c.name as customer_name, cs.specialty_id, s.name as specialty_name  " +
            "FROM customers c join customers_specialties cs" +
            " ON c.id = cs.customer_id join specialties s ON s.id = cs.specialty_id join accounts a" +
            " ON a.id = c.account_id join accountstatuses acs ON a.accountstatus_id = acs.id" +
            " WHERE c.id = %d"),
    CREATE_CUSTOMER("insert into customers (name, account_id) value ('%s', %d)"),
    IS_EXIST_CUSTOMER("SELECT EXISTS(SELECT id FROM customers WHERE id = %d)"),
    DELETE_CUSTOMER("" +
            "DELETE customers, accounts\n" +
            "FROM customers\n" +
            "  INNER JOIN accounts ON accounts.id = customers.account_id\n" +
            "WHERE customers.id=%d"),
    UPDATE_CUSTOMER("" +
            "UPDATE customers set\n" +
            "id=%d,\n" +
            "name='%s' where id = %d"),

    GET_ALL_CUSTOMERS("" +
            "SELECT a.id as account_id,  acs.id as accountstatus_id, c.id as customer_id, c.name as customer_name, cs.specialty_id, s.name as specialty_name  " +
            "FROM customers c join customers_specialties cs" +
            " ON c.id = cs.customer_id join specialties s ON s.id = cs.specialty_id join accounts a" +
            " ON a.id = c.account_id join accountstatuses acs ON a.accountstatus_id = acs.id"),


    CREATE_ACCOUNT("INSERT INTO accounts (accountstatus_id) value (%d)"),
    UPDATE_ACCOUNT("UPDATE accounts SET  accountstatus_id = %d WHERE id = %d"),
    DELETE_ACCOUNT("DELETE accounts FROM accounts where accounts.id = %d"),
    GET_ALL_ACCOUNTS("SELECT * FROM accounts"),
    GET_ALL_CUSTOMERS_ACCOUNTS("SELECT  c.id as customer_id,\n" +
                                       "a.id as account_id ,ac.name as accountstatus  from customers c\n" +
                                       "join accounts a on c.account_id=a.id\n" +
                                       "join accountstatuses ac on a.accountstatus_id=ac.id"),

    GET_ACCOUNT_BY_ID("SELECT * FROM accounts WHERE id = %d"),
    INSERT_ACCOUNT_STATUS_IN_CUSTOMER("INSERT INTO accounts (accountstatus_id) value (%d)"),

    GET_ALL_SPECIALTIES("SELECT * FROM specialties"),
    GET_ALL_SPECIALTIES_EXCEPT_HAVE_CUSTOMERS("" +
                                                      "SELECT specialties.id as id, specialties.name as name FROM specialties " +
                                                      "WHERE specialties.id  NOT IN" +
                                                      "(SELECT specialty_id FROM customers " +
                                                      "   INNER JOIN customers_specialties " +
                                                      "ON customers.id = customers_specialties.customer_id " +
                                                      "WHERE customers.id = %d)"),
    IS_EXIST_SPECIALTY("SELECT EXISTS(SELECT id FROM specialties WHERE id = %d)"),
    IS_EXIST_SPECIALTY_IN_CUSTOMER("SELECT EXISTS(SELECT specialty_id FROM customers_specialties WHERE customer_id = %d AND specialty_id = %d)"),
    GET_SPECIALTIES_CUSTOMER("SELECT * FROM specialties Where customer_id = %s"),
    GET_SPECIALTY_BY_ID("SELECT * FROM specialties WHERE id = %d"),
    INSERT_SPECIALTY_IN_CUSTOMER("INSERT INTO customers_specialties(customer_id, specialty_id) value(%d, %d)"),

    INSERT_SPECIALTY("INSERT INTO specialties (specialties.id, specialties.name) value (%d, %s)"),

    CREATE_SPECIALTY("INSERT INTO specialties (name) value ('%s')"),

    DELETE_SPECIALTY("DELETE specialties FROM specialties where specialties.id = %d"),
    DELETE_SPECIALTY_FROM_CUSTOMER("" +
                                           "DELETE customers_specialties " +
                                           "FROM customers_specialties " +
                                           "WHERE customer_id = %d " +
                                           "AND specialty_id = %d"),

    GET_SET_SPECIALTIES_CUSTOMER("" +
                                         "SELECT cs.specialty_id AS id, s.name" +
                                         " FROM customers c\n" +
                                         "  INNER JOIN customers_specialties cs on c.id = cs.customer_id\n" +
                                         "  INNER JOIN specialties s on cs.specialty_id = s.id\n" +
                                         " WHERE c.id = %d"),

    PREPARED_GET_SET_SPECIALTIES_CUSTOMER("" +
                                                  "SELECT cs.specialty_id AS id, s.name" +
                                                  " FROM customers c" +
                                                  "  INNER JOIN customers_specialties cs on c.id = cs.customer_id" +
                                                  "  INNER JOIN specialties s on cs.specialty_id = s.id" +
                                                  " WHERE c.id = ?");


    private final String query;


    public String formatSql(Object... params) {
        return String.format(this.query, params);
    }
}
