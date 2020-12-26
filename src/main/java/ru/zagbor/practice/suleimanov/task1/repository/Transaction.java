package ru.zagbor.practice.suleimanov.task1.repository;

import java.sql.SQLException;
import java.sql.Statement;

public interface Transaction {

    Statement createTransaction();

    void closeTransaction() throws SQLException;
}
