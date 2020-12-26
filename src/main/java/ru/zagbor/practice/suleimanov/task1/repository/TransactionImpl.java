package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.base.initialization.BaseInitialization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionImpl implements Transaction {


    private final String URL = BaseInitialization.getURL();
    private final String USERNAME = BaseInitialization.getUSERNAME();
    private final String PASSWORD = BaseInitialization.getPASSWORD();

    private Statement statement;

    public TransactionImpl() {

    }

    public Statement createTransaction() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            statement.execute("START TRANSACTION");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public void closeTransaction() throws SQLException {
        statement.execute("COMMIT");
        statement.close();
    }
}
