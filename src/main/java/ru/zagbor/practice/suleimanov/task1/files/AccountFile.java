package ru.zagbor.practice.suleimanov.task1.files;

import java.io.File;
import java.io.IOException;

public class AccountFile implements FileSystem {
    @Override
    public void create() throws IOException {
     File accounts = new File("Accounts.json");
        accounts.createNewFile();
    }
}
