package ru.zagbor.practice.suleimanov.task1.repository;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface CrudRepository {

    void execute(Statement statement, String query) throws SQLException;

    void insert(Statement statement, String tableName, List<String> columns, List<String> values) throws SQLException;

    CachedRowSet selectForId(Statement statement, String tableName, String nameColumnId, String id) throws SQLException;

    CachedRowSet executeQuery(Statement statement, String query) throws SQLException;

    Long findMaxId(Statement statement, String table, String column) throws SQLException;

    void executeUpdate(Statement statement, String query) throws SQLException;

}
