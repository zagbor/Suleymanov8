package ru.zagbor.practice.suleimanov.task1;

import ru.zagbor.practice.suleimanov.task1.db.DBInit;
import ru.zagbor.practice.suleimanov.task1.view.GeneralMenu;
import ru.zagbor.practice.suleimanov.task1.view.MenuSelect;

import java.io.IOException;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws IOException, SQLException {
        DBInit.init();
        new MenuSelect(new GeneralMenu()).executePlay();
    }
}
