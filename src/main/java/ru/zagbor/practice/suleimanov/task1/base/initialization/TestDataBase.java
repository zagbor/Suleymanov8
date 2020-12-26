package ru.zagbor.practice.suleimanov.task1.base.initialization;

import liquibase.exception.DatabaseException;

import java.sql.SQLException;

public interface TestDataBase {
    void create() throws SQLException, DatabaseException;
}
