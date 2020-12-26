package ru.zagbor.practice.suleimanov.task1.builders;

import ru.zagbor.practice.suleimanov.task1.model.Customer;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBuilder  {

    public List<Customer> builderListCustomers(CachedRowSet cachedRowSet) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        while (cachedRowSet.next()) {
            customers.add(builder(cachedRowSet));
        }
        return customers;
    }

    public Customer builder(CachedRowSet cachedRowSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(cachedRowSet.getInt("id"));
        customer.setName(cachedRowSet.getString("name"));
        return customer;
    }


}
