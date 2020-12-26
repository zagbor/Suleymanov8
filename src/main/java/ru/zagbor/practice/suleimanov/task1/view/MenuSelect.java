package ru.zagbor.practice.suleimanov.task1.view;


import java.io.IOException;
import java.sql.SQLException;

public class MenuSelect {
    Menu menu;

    public MenuSelect(Menu menu) {
        this.menu = menu;
    }

    public void executePlay() throws IOException, SQLException {
        menu.execute();
    }
}
