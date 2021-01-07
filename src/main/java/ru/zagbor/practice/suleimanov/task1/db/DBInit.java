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
            ConnectionFactory.init(connectionsProperties);
            ConnectionFactory.isConnectionPossible();
        } catch (IOException | IllegalStateException| SQLException s){
            connectionsProperties = dbController.getDataUser();
            DatabaseConnectionsPropertiesManager.writeConnectionProperties(connectionsProperties);
            ConnectionFactory.init(connectionsProperties);
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