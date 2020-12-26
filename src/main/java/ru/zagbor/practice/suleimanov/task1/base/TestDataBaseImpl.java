package ru.zagbor.practice.suleimanov.task1.base;

import liquibase.exception.DatabaseException;
import ru.zagbor.practice.suleimanov.task1.base.initialization.TestDataBase;

import java.sql.SQLException;

public class TestDataBaseImpl implements TestDataBase {

    public void create() throws SQLException, DatabaseException {
        BaseVersionControllerImpl baseVersionController = new BaseVersionControllerImpl();
        baseVersionController.executeLiquidBaseScripts("WEB-INF\\classes\\db.changelog\\insert-data.xml");
    }

}
