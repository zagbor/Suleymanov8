package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.builders.AccountBuilder;
import ru.zagbor.practice.suleimanov.task1.builders.CustomerBuilder;
import ru.zagbor.practice.suleimanov.task1.builders.SpecialtyBuilder;
import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final CrudRepository crudRepository;

    public CustomerRepositoryImpl() {
        crudRepository = new CrudRepositoryImpl();
    }

    public CustomerRepositoryImpl(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Customer update(Customer customer) throws SQLException, IOException {
        Transaction transaction = new TransactionImpl();
        Statement statement = transaction.createTransaction();
        crudRepository.executeUpdate(statement, "DELETE customers, accounts\n" +
                "FROM customers\n" +
                "INNER JOIN accounts ON accounts.id = customers.account_id\n" +
                "WHERE customers.id=" + customer.getId());
        if (customer.getId() == 0) {
            customer.setId(crudRepository.findMaxId(statement, "customers", "id") + 1);
        }
        if (customer.getAccount().getId() == 0) {
            customer.getAccount().setId(crudRepository.findMaxId(statement, "accounts", "id") + 1);
        }
        crudRepository.insert(statement, "accounts", List.of("id", "accountstatus_id"),
                List.of(Long.toString(customer.getAccount().getId()),
                        Integer.toString(customer.getAccount().getAccountStatus().getId())));
        crudRepository.insert(statement, "customers", List.of("id", "name", "account_id"),
                List.of(Long.toString(customer.getId()), customer.getName(), Long.toString(customer.getAccount().getId())));
        customer.getSpecialties().forEach(specialty -> {
            try {
                crudRepository.insert(statement, "customers_specialties",
                        List.of("customer_id", "specialty_id"), List.of(Long.toString(customer.getId()), Long.toString(specialty.getId())));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
        transaction.closeTransaction();
        return customer;
    }


    @Override
    public List<Customer> getAll() throws SQLException {
        Transaction transaction = new TransactionImpl();
        Statement statement = transaction.createTransaction();
        CachedRowSet cachedRowSetCustomers = getAllModelCustomers(statement);
        CachedRowSet cachedRowSetSpecialties = getAllSpecialtiesCustomers(statement);
        CachedRowSet cachedRowSetAccounts = getAllCustomersAccountQuery(statement);
        transaction.closeTransaction();
        Map<Long, List<Specialty>> idCustomersSpecialtyMap = new SpecialtyBuilder().builderListSpecialtyCustomers(cachedRowSetSpecialties);
        Map<Long, Account> idCustomersAccountMap = new AccountBuilder().builderMapIdCustomersAccount(cachedRowSetAccounts);
        return customersCollector(cachedRowSetCustomers, idCustomersSpecialtyMap, idCustomersAccountMap);
    }

    @Override
    public Optional<Customer> getById(Long id) throws IOException, SQLException {
        Transaction transaction = new TransactionImpl();
        Statement statement = transaction.createTransaction();
        CachedRowSet cachedRowSet = crudRepository.selectForId(statement, "customers", "id", Long.toString(id));
        List<Customer> customers = new CustomerBuilder().builderListCustomers(cachedRowSet);
        if (customers.size() > 0) {
            Customer customer = customers.get(0);
            Set<Specialty> specialties = getCustomerSpecialties(statement, Long.toString(id));
            Account account = getCustomerAccount(statement, Long.toString(id));
            customer.setSpecialties(specialties);
            customer.setAccount(account);
            return Optional.of(customers.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) throws IOException, SQLException {
        Transaction transaction = new TransactionImpl();
        Statement statement = transaction.createTransaction();
        crudRepository.executeUpdate(statement, "DELETE customers, accounts\n" +
                "FROM customers\n" +
                "INNER JOIN accounts ON accounts.id = customers.account_id\n" +
                "WHERE customers.id=" + id);
        transaction.closeTransaction();
    }

    @Override
    public Customer create(Customer customer) throws IOException, SQLException {
        Transaction transaction = new TransactionImpl();
        Statement statement = transaction.createTransaction();
        if (customer.getId() == 0) {
            customer.setId(crudRepository.findMaxId(statement, "customers", "id") + 1);
        }
        if (customer.getAccount().getId() == 0) {
            customer.getAccount().setId(crudRepository.findMaxId(statement, "accounts", "id") + 1);
        }
        crudRepository.insert(statement, "accounts", List.of("id", "accountstatus_id"),
                List.of(Long.toString(customer.getAccount().getId()),
                        Integer.toString(customer.getAccount().getAccountStatus().getId())));
        crudRepository.insert(statement, "customers", List.of("id", "name", "account_id"),
                List.of(Long.toString(customer.getId()), customer.getName(), Long.toString(customer.getAccount().getId())));
        customer.getSpecialties().forEach(specialty -> {
            try {
                crudRepository.insert(statement, "customers_specialties",
                        List.of("customer_id", "specialty_id"), List.of(Long.toString(customer.getId()), Long.toString(specialty.getId())));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
        return customer;
    }

    @Override
    public void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException, SQLException {
        customer.getSpecialties().remove(specialty);
        this.update(customer);
    }


    private List<Customer> customersCollector(CachedRowSet cachedRowSetCustomers,
                                              Map<Long, List<Specialty>> idCustomersSpecialtyMap,
                                              Map<Long, Account> idCustomersAccountMap) throws SQLException {
        List<Customer> customers = new CustomerBuilder().builderListCustomers(cachedRowSetCustomers);
        customers = getCustomersWithSpecialties(customers, idCustomersSpecialtyMap);
        return getCustomersWithAccounts(customers, idCustomersAccountMap);

    }

    private List<Customer> getCustomersWithAccounts(List<Customer> customers, Map<Long, Account> idCustomersAccountMap) {
        return customers.stream().peek(customer -> {
            if (idCustomersAccountMap.containsKey(customer.getId())) {
                customer.setAccount(idCustomersAccountMap.get(customer.getId()));
            } else {
                customer.setAccount(new Account());
            }
        }).collect(Collectors.toList());
    }

    private Account getCustomerAccount(Statement statement, String id) throws SQLException {
        CachedRowSet cachedRowSet = getCustomerAccountQuery(statement, id);
        List<Account> accounts = new AccountBuilder().builderListAccounts(cachedRowSet);
        if (accounts.size() != 0) {
            return accounts.get(0);
        } else {
            return new Account();
        }
    }

    private CachedRowSet getCustomerAccountQuery(Statement statement, String id) throws SQLException {
        return crudRepository.executeQuery(statement, "SELECT c.id, ac.name FROM customers c\n" +
                "join accounts a  on c.account_id =a.id\n" +
                "join accountstatuses ac on a.accountstatus_id=ac.id\n" +
                "where c.id =" + id);
    }

    private CachedRowSet getAllCustomersAccountQuery(Statement statement) throws SQLException {
        return crudRepository.executeQuery(statement, "SELECT  c.id as customer_id," +
                " a.id as account_id ,ac.name as accountstatus  from customers c\n" +
                "join accounts a on c.account_id=a.id\n" +
                "join accountstatuses ac on a.accountstatus_id=ac.id");
    }


    private List<Customer> getCustomersWithSpecialties(List<Customer> customers, Map<Long, List<Specialty>> idCustomersSpecialtyMap) {
        return customers.stream().peek(customer -> {
            if (idCustomersSpecialtyMap.containsKey(customer.getId())) {
                Set<Specialty> specialties = Set.copyOf(idCustomersSpecialtyMap.get(customer.getId()));
                customer.setSpecialties(specialties);
            } else {
                customer.setSpecialties(new HashSet<>());
            }
        }).collect(Collectors.toList());

    }

    private CachedRowSet getAllModelCustomers(Statement statement) throws SQLException {
        return crudRepository.executeQuery(statement, "SELECT * from customers");
    }

    private CachedRowSet getAllSpecialtiesCustomers(Statement statement) throws SQLException {
        return crudRepository.executeQuery(statement,
                "SELECT c.id as customer_id, s.id as specialty_id, s.name as specialty_name\n" +
                        "  FROM customers c\n" +
                        "JOIN customers_specialties cs\n" +
                        "  ON c.id=cs.customer_id\n" +
                        "join specialties s\n" +
                        "on s.id=cs.specialty_id");
    }

    private Set<Specialty> getCustomerSpecialties(Statement statement, String id) throws SQLException {
        CachedRowSet cachedRowSet = getCustomerSpecialtiesQuery(statement, id);
        return getCustomerSpecialtiesParse(cachedRowSet);

    }


    private CachedRowSet getCustomerSpecialtiesQuery(Statement statement, String id) throws SQLException {
        return crudRepository.executeQuery(statement, "SELECT" +
                " s.id as id, s.name as name\n" +
                "  FROM customers c\n" +
                "JOIN customers_specialties cs\n" +
                "  ON c.id=cs.customer_id\n" +
                "join specialties s\n" +
                "on s.id=cs.specialty_id\n" +
                "where c.id=" + id);
    }

    private Set<Specialty> getCustomerSpecialtiesParse(CachedRowSet cachedRowSet) throws SQLException {
        return new SpecialtyBuilder().builderListSpecialty(cachedRowSet);
    }

    public boolean isCustomerExist(long id) throws IOException, SQLException {
        Transaction transaction = new TransactionImpl();
        Statement statement = transaction.createTransaction();
        CachedRowSet cachedRowSet = crudRepository.executeQuery(statement, "SELECT id FROM customers WHERE id =" + id);
        transaction.closeTransaction();
        cachedRowSet.first();
        Long customerId = cachedRowSet.getLong("id");
        return customerId != null;

    }


}
