package ru.zagbor.practice.suleimanov.task1.db;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.String.format;

@RequiredArgsConstructor
public class DatabaseConnectionsPropertiesManagerImpl implements DatabaseConnectionsPropertiesManager {

    private static final String DEFAULT_FILE_NAME = "BaseInformation.txt";

    private final DBController dbController;

    @Override
    public DatabaseConnectionsProperties loadDatabaseConnectionProperties() throws MissingDatabaseConnectionProperties {
        // 1
        DatabaseConnectionsProperties connectionsProperties = loadConnectionProperties();

        // 2
        boolean isOk = checkDatabaseConnectionProperties(connectionsProperties);

        // 3
        if (!isOk) {
            // 3.1
            connectionsProperties = dbController.getDataUser();
            writeConnectionProperties(connectionsProperties);
            connectionsProperties = loadConnectionProperties();
            if (!checkDatabaseConnectionProperties(connectionsProperties)) {
                throw new MissingDatabaseConnectionProperties("Настройки базы не найдены");
            }
            // 3.2

        }

        return connectionsProperties;
    }

    private boolean checkDatabaseConnectionProperties(DatabaseConnectionsProperties connectionsProperties) {
        if (connectionsProperties == null) {
            return false;
        }
        return ConnectionFactory.checkProperties(connectionsProperties);
    }

    private void writeConnectionProperties(DatabaseConnectionsProperties properties) {
        createInitFile();
        write(properties);
    }

    private void write(DatabaseConnectionsProperties properties) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DEFAULT_FILE_NAME))) {
            writer.write(properties.getUrl() + "\n" + properties.getUsername() + "\n" + properties.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DatabaseConnectionsProperties loadConnectionProperties() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DEFAULT_FILE_NAME))) {
            DatabaseConnectionsProperties properties = new DatabaseConnectionsProperties();
            properties.setUrl(reader.readLine());
            properties.setUsername(reader.readLine());
            properties.setPassword(reader.readLine());
            return properties;
        } catch (IOException e) {
            System.out.println(format("File [%s] not found", DEFAULT_FILE_NAME));
            // create
            // lead
            return null;
            // throw e;
            // return new DatabaseConnectionsProperties();
            // return Optional.ofNullable();

            // пряная рекурсия
        }
    }

    private static boolean createInitFile() {
        File initializationFile = new File(DEFAULT_FILE_NAME);
        try {
            boolean exists = initializationFile.exists();
            if (exists) {
                deleteInitFile();
            }
            return initializationFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean deleteInitFile() {
        File file = new File(DEFAULT_FILE_NAME);
        return file.delete();
    }
}
