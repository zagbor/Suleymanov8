package ru.zagbor.practice.suleimanov.task1.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;
import ru.zagbor.practice.suleimanov.task1.model.Specialty;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final String CUSTOMERS = "Customers.json";
    private final AccountRepository accountRepository;

    public CustomerRepositoryImpl() {
        accountRepository = new AccountRepositoryImpl();
    }

    @Override
    public Customer update(Customer customer) throws IOException {
        if (customer.getId() == 0 || !isCustomerExist(customer.getId())) {
            throw new IllegalStateException();
        }
        List<Customer> updatingCustomers = deleteCustomerByIdFromList(customer.getId(), getAll());
        updatingCustomers.add(customer);
        writeCustomers(updatingCustomers);
        return customer;
    }

    @Override
    public Optional<Customer> getById(Long id) throws IOException {
        return getAll().stream()
                .filter(customer -> customer.getId() == id)
                .findFirst();
    }

    @Override
    public Customer create(Customer customer) throws IOException {
        if (customer.getId() != 0) {
            throw new IllegalStateException();
        }
        prepareCreate(customer);
        List<Customer> customers = getAll();
        customers.add(customer);
        writeCustomers(customers);
        return customer;
    }

    @Override
    public void deleteById(Long id) throws IOException {
        List<Customer> customers = getAll();
        if (!getById(id).isPresent()) {
            return;
        }
        Files.newBufferedWriter(Paths.get(CUSTOMERS), StandardOpenOption.TRUNCATE_EXISTING);
        accountRepository.deleteAll();
        List<Customer> resultCustomers = deleteCustomerByIdFromList(id, customers);
        writeCustomers(resultCustomers);

    }

    @Override
    public List<Customer> getAll() throws IOException {

        Type itemsListType = new TypeToken<List<Customer>>() {
        }.getType();
        String data = new String(Files.readAllBytes(Paths.get(CUSTOMERS)), StandardCharsets.UTF_8);
        if (data.isEmpty()) {
            return new ArrayList<Customer>();
        }
        return new Gson().fromJson(data, itemsListType);
    }


    public void addSpecialtyCustomer(long customerId, Specialty specialty) throws IOException {
        Customer customer = getById(customerId).get();
        customer.getSpecialties().add(specialty);
        this.update(customer);
    }

    public void changeName(Customer customer, String name) throws IOException {
        customer.setName(name);
        this.update(customer);
    }

    public void changeAccountStatus(Customer customer, Account.AccountStatus accountStatus) throws IOException {
        customer.getAccount().setAccountStatus(accountStatus);
        this.update(customer);
    }

    public boolean isCustomerExist(long id) throws IOException {
        List<Customer> customers = this.getAll();
        return customers.stream().anyMatch(customer -> customer.getId() == id);
    }

    public void deleteSpecialtyCustomer(Customer customer, Specialty specialty) throws IOException {
        customer.getSpecialties().remove(specialty);
        this.update(customer);
    }


    private void createAccount(Customer customer) throws IOException {
        Account account = new Account();
        account.setAccountStatus(customer.getAccount().getAccountStatus());
        customer.setAccount(accountRepository.create(account));
    }

    private Long findMaxId() throws IOException {
        return this.getAll().stream()
                .map(customer -> customer.getId())
                .max(Comparator.naturalOrder()).orElse(0L);
    }

    private void writeCustomers(List<Customer> customers) throws IOException {
        Files.newBufferedWriter(Paths.get(CUSTOMERS), StandardOpenOption.TRUNCATE_EXISTING);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS, true))) {
            String sCustomer = new Gson().toJson(customers);
            writer.write(sCustomer);
            writer.flush();
        }
    }

    private List<Customer> deleteCustomerByIdFromList(long id, List<Customer> customers) {
        return customers.stream()
                .filter(customer -> customer.getId() != id).collect(Collectors.toList());
    }

    private void prepareCreate(Customer customer) throws IOException {
        createAccount(customer);
        customer.setId(findMaxId() + 1);
    }
}
