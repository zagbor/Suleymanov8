package ru.zagbor.practice.suleimanov.task1.repository;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.zagbor.practice.suleimanov.task1.model.Account;
import ru.zagbor.practice.suleimanov.task1.model.Customer;

import java.io.BufferedWriter;
import java.io.File;
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

public class AccountRepositoryImpl implements AccountRepository {
    private final String ACCOUNTS = "Accounts.json";

    public AccountRepositoryImpl() {
    }

    public void deleteAll() throws IOException {
        Files.newBufferedWriter(Paths.get(ACCOUNTS), StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Override
    public Optional<Account> getById(Long id) throws IOException {
        List<Account> specialties = getAll();
        return specialties.stream()
                .filter(specialty -> specialty.getId() == id)
                .findFirst();
    }


    @Override
    public Account update(Account account) throws IOException {
        if (account.getId() <= 0) {
            account.setId(findMaxId() + 1);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS, true))) {
            if (new File(ACCOUNTS).length() != 0) {
                writer.newLine();
            }
            writer.write(account.getId() + ";" + account.getAccountStatus());
            writer.flush();
        }
        return account;
    }


    @Override
    public void deleteById(Long id) throws IOException {
        List<Account> accounts = this.getAll();
        if (!getById(id).isPresent()) {
            return;
        }
        Files.newBufferedWriter(Paths.get(ACCOUNTS), StandardOpenOption.TRUNCATE_EXISTING);
        List<Account> updateAccounts = deleteAccountByIdFromList(id, accounts);
        writeAccounts(updateAccounts);
    }

    @Override
    public List<Account> getAll() throws IOException {
        Type itemsListType = new TypeToken<List<Account>>() {
        }.getType();

        String data = new String(Files.readAllBytes(Paths.get(ACCOUNTS)), StandardCharsets.UTF_8);
        if (data.isEmpty()) {
            return new ArrayList<Account>();
        }
        return new Gson().fromJson(data, itemsListType);
    }

    @Override
    public Account create(Account account) throws IOException {
        if (account.getId() != 0) {
            throw new IllegalStateException();
        }
        account.setId(findMaxId() + 1);
        List<Account> accounts = this.getAll();
        accounts.add(account);
        writeAccounts(accounts);
        return account;
    }


    private Long findMaxId() throws IOException {

        return getAll().stream()
                .map(account -> account.getId())
                .max(Comparator.naturalOrder()).orElse(10000L);
    }

    private List<Account> deleteAccountByIdFromList(long id, List<Account> customers) {
        return customers.stream()
                .filter(customer -> customer.getId() != id).collect(Collectors.toList());
    }

    private void writeAccounts(List<Account> accounts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS, true))) {
            String sCustomer = new Gson().toJson(accounts);
            writer.write(sCustomer);
        }
    }

}
