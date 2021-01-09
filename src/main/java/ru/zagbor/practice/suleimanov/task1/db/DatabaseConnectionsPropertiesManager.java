package ru.zagbor.practice.suleimanov.task1.db;

public interface DatabaseConnectionsPropertiesManager {

    DatabaseConnectionsProperties loadDatabaseConnectionProperties() throws MissingDatabaseConnectionProperties;

    public static class MissingDatabaseConnectionProperties extends Exception {

        public MissingDatabaseConnectionProperties() {
        }

        public MissingDatabaseConnectionProperties(String message) {
            super(message);
        }

        public MissingDatabaseConnectionProperties(String message, Throwable cause) {
            super(message, cause);
        }

        public MissingDatabaseConnectionProperties(Throwable cause) {
            super(cause);
        }

        public MissingDatabaseConnectionProperties(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
