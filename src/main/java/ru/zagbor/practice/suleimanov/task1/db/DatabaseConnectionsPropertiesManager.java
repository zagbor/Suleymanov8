package ru.zagbor.practice.suleimanov.task1.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.String.format;

public class DatabaseConnectionsPropertiesManager {

    private static final String DEFAULT_FILE_NAME = "BaseInformation.txt";

    public static void writeConnectionProperties(DatabaseConnectionsProperties properties) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DEFAULT_FILE_NAME))) {
            writer.write(properties.getUrl() + "\n" + properties.getUsername() + "\n" + properties.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnectionsProperties loadConnectionProperties() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(DEFAULT_FILE_NAME))) {
            DatabaseConnectionsProperties properties = new DatabaseConnectionsProperties();
            properties.setUrl(reader.readLine());
            properties.setUsername(reader.readLine());
            properties.setPassword(reader.readLine());
            return properties;
        } catch (IOException e) {
            System.out.println(format("File [%s] not found: create new", DEFAULT_FILE_NAME));
            createInitFile();
            throw e;
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

