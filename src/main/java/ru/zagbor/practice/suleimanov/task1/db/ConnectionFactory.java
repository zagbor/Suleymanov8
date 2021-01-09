package ru.zagbor.practice.suleimanov.task1.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static DatabaseConnectionsProperties properties;

    public static void init(DatabaseConnectionsProperties properties) {
        ConnectionFactory.properties = properties;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }

    public static boolean checkProperties(DatabaseConnectionsProperties properties) {
        try {
            DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
