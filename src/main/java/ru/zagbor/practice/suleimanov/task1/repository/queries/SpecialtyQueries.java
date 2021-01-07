package ru.zagbor.practice.suleimanov.task1.repository.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SpecialtyQueries {
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

