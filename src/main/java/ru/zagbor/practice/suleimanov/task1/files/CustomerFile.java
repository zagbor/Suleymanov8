package ru.zagbor.practice.suleimanov.task1.files;

import java.io.IOException;

public class CustomerFile implements FileSystem {
    @Override
    public void create() throws IOException {
        java.io.File customers = new java.io.File("Customers.json");
        customers.createNewFile();
    }
}
