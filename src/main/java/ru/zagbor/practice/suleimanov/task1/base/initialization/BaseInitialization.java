package ru.zagbor.practice.suleimanov.task1.base.initialization;

import liquibase.exception.DatabaseException;
import ru.zagbor.practice.suleimanov.task1.base.BaseVersionControllerImpl;
import ru.zagbor.practice.suleimanov.task1.base.TestDataBaseImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class BaseInitialization {

    private final TestDataBase testDataBase;

    public BaseInitialization() {
        testDataBase = new TestDataBaseImpl();
    }

    private static final String fileName = "BaseInformation.txt";
    private static String URL = null;
    private static String USERNAME = null;
    private static String PASSWORD = null;
    final static String CHANGELOG = BaseVersionControllerImpl.getCHANGELOG();


    public static String getURL() {
        return URL;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }


    private static void create() throws IOException, SQLException, DatabaseException {
        java.io.File initializationFile = new java.io.File("BaseInformation.txt");
        initializationFile.createNewFile();
        getDataUser();
        writeData();
        initialization();
        new BaseVersionControllerImpl().executeLiquidBaseScripts(CHANGELOG);
        addTestDataBase();
    }

    private static void writeData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(URL + "\n" + USERNAME + "\n" + PASSWORD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getDataUser() throws IOException {
        System.out.println("Данные для подключения к базе не найдены. Просим ввести их.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите URL вашей базы:");
        URL = reader.readLine();
        System.out.println("Введите USERNAME вашей базы:");
        USERNAME = reader.readLine();
        System.out.println("Введите PASSWORD вашей базы:");
        PASSWORD = reader.readLine();

    }

    public static void initialization() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            URL = reader.readLine();
            USERNAME = reader.readLine();
            PASSWORD = reader.readLine();
        } catch (FileNotFoundException e) {
            try {
                create();
            } catch (IOException | SQLException | DatabaseException ioException) {
                ioException.printStackTrace();
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTestDataBase() throws IOException, SQLException, DatabaseException {
        final TestDataBase testDataBase = new TestDataBaseImpl();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Хотите добавить тестовые данные в базу? Если да, то введите y, если нет, то e на английском языке.");
        String s = reader.readLine();
        if (s.equals("y")) {
            testDataBase.create();
        }
    }


    public static void deleteInitializationFile() {
        File file = new File("BaseInformation.txt");
        file.delete();
    }
}

