package ru.zagbor.practice.suleimanov.task1.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {


    public Utils() {
    }

    public static Long getId(Statement statement) throws SQLException {
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.first();
        return resultSet.getLong(1);

    }


}
