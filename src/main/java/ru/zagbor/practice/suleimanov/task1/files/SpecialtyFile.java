package ru.zagbor.practice.suleimanov.task1.files;

import java.io.File;
import java.io.IOException;

public class SpecialtyFile implements FileSystem {
    @Override
    public void create() throws IOException {
        File specialties = new File("Specialties.json");
        specialties.createNewFile();
    }
}
