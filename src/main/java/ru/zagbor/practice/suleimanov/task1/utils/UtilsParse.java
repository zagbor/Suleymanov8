package ru.zagbor.practice.suleimanov.task1.utils;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public class UtilsParse {


    public static long parseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
