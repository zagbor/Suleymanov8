package ru.zagbor.practice.suleimanov.task1.db;

import liquibase.exception.DatabaseException;
import ru.zagbor.practice.suleimanov.task1.db.DatabaseConnectionsPropertiesManager.MissingDatabaseConnectionProperties;

public class DBInit {

    private final DBController dbController = new DBController();
    private final LiquibaseVersionManager liquibaseVersionManager = new LiquibaseVersionManager();
    private final DatabaseConnectionsPropertiesManager databaseConnectionsPropertiesManager =
            new DatabaseConnectionsPropertiesManagerImpl(dbController);

    public static void init() {
        DBInit initializer = new DBInit();
        initializer.execute();
    }

    private void execute() {
        DatabaseConnectionsProperties properties = null;
        try {
            properties = databaseConnectionsPropertiesManager.loadDatabaseConnectionProperties();
        } catch (MissingDatabaseConnectionProperties e) {
            System.err.println(e.getMessage());
            return;
        }
        ConnectionFactory.init(properties);

        try {
            initDatabaseSchema();
        } catch (DatabaseException e) {
            System.err.println("Что-то пошло не так: " + e.getMessage());
        }
    }

    private void initDatabaseSchema() throws DatabaseException {
        liquibaseVersionManager.initDatabaseSchema();
        boolean userDecision = dbController.getUserDecision();
        if (userDecision) {
            liquibaseVersionManager.initTestData();
        }
    }
}