package ru.zagbor.practice.suleimanov.task1.repository;

import ru.zagbor.practice.suleimanov.task1.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementFactory {

    private static Connection connection = ConnectionFactory.getConnection();

    public static Statement getStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static PreparedStatement getPrepareStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
