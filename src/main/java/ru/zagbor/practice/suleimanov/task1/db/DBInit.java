package ru.zagbor.practice.suleimanov.task1.db;

import liquibase.exception.DatabaseException;

import java.io.IOException;
import java.sql.SQLException;

public class DBInit {

    private final DBController dbController = new DBController();
    private final LiquibaseVersionManager liquibaseVersionManager = new LiquibaseVersionManager();

    public static void init() {
        DBInit initializer = new DBInit();
        initializer.execute();
    }

    private void execute() {
        DatabaseConnectionsProperties connectionsProperties = null;
        try {
            connectionsProperties = DatabaseConnectionsPropertiesManager.loadConnectionProperties();
            boolean propertiesIsOK = ConnectionFactory.checkProperties(connectionsProperties);
            if (!propertiesIsOK) {
                connectionsProperties = dbController.getDataUser();
                DatabaseConnectionsPropertiesManager.writeConnectionProperties(connectionsProperties);
            }
            ConnectionFactory.init(connectionsProperties);
        } catch (IOException | SQLException s) {
            System.err.println("Что-то пошло не так...");
        }
        try {
            liquibaseVersionManager.initDatabaseSchema();
            boolean userDecision = dbController.getUserDecision();
            if (userDecision) {
                liquibaseVersionManager.initTestData();
            }
        } catch (
                DatabaseException e) {
            e.printStackTrace();
        }
    }
}