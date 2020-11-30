package ru.zagbor.practice.suleimanov.task1;

import ru.zagbor.practice.suleimanov.task1.files.StorageInitializer;
import ru.zagbor.practice.suleimanov.task1.view.GeneralMenu;
import ru.zagbor.practice.suleimanov.task1.view.MenuSelect;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        StorageInitializer.createFiles();
        new MenuSelect(new GeneralMenu()).executePlay();

    }
}
