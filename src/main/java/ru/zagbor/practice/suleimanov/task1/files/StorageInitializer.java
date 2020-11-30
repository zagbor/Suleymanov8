package ru.zagbor.practice.suleimanov.task1.files;


import java.io.IOException;


public class StorageInitializer {

    public static void createFiles() throws IOException {
        new AccountFile().create();
        new SpecialtyFile().create();
        new CustomerFile().create();
    }
}
