package ru.zagbor.practice.suleimanov.task1;

import liquibase.exception.DatabaseException;
import ru.zagbor.practice.suleimanov.task1.base.BaseVersionControllerImpl;
import ru.zagbor.practice.suleimanov.task1.base.initialization.BaseInitialization;
import ru.zagbor.practice.suleimanov.task1.view.GeneralMenu;
import ru.zagbor.practice.suleimanov.task1.view.MenuSelect;

import java.io.IOException;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws IOException, DatabaseException, SQLException {
        final String CHANGELOG = BaseVersionControllerImpl.getCHANGELOG();
        BaseInitialization.initialization();
        new BaseVersionControllerImpl().executeLiquidBaseScripts(CHANGELOG);
        new MenuSelect(new GeneralMenu()).executePlay();

    }
}
