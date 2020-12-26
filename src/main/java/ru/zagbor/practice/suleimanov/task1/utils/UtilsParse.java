package ru.zagbor.practice.suleimanov.task1.utils;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public class UtilsParse {

    public static Long idParser(CachedRowSet cachedRowSet) throws SQLException {
        Long l = null;
        while (cachedRowSet.next()) {
            l = cachedRowSet.getLong("id");
        }
        return l;
    }
}
