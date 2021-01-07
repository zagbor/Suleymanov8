package ru.zagbor.practice.suleimanov.task1.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DBController {

    private final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public DatabaseConnectionsProperties getDataUser() {
        System.out.println("Введите данные для поключения.");
        System.out.println("Введите URL вашей базы:");
        try {
            DatabaseConnectionsProperties properties = new DatabaseConnectionsProperties();
            properties.setUrl(reader.readLine());
            System.out.println("Введите USERNAME вашей базы:");
            properties.setUsername(reader.readLine());
            System.out.println("Введите PASSWORD вашей базы:");
            properties.setPassword(reader.readLine());
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DatabaseConnectionsProperties();
    }

    public boolean getUserDecision() {
        System.out.println("Хотите добавить тестовые данные в базу? Если да, то введите y, если нет, то e на английском языке.");
        try {
            String s = reader.readLine();
            return s.equals("y");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
