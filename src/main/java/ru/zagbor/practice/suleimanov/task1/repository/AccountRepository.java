package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.model.Account;

public interface AccountRepository extends GenericRepository<Account, Long> {
    void saveAccountStatus(Long idCustomer, Integer idAccountStatus);

    void deleteAll();
}
