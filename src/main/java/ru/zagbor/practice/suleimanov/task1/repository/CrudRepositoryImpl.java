package ru.zagbor.practice.suleimanov.task1.repository;


import org.apache.commons.lang3.StringUtils;
import ru.zagbor.practice.suleimanov.task1.utils.UtilsParse;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CrudRepositoryImpl implements CrudRepository {



    public void simpleDelete(Statement statement, String tableName, String nameColumnId, String id) throws SQLException {
            statement.executeUpdate("DELETE from " + tableName + " WHERE " + nameColumnId + " = " + id);
    }


    public CachedRowSet selectForId(Statement statement, String tableName, String nameColumnId, String id) throws SQLException {
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet cachedRowSet = factory.createCachedRowSet();
            cachedRowSet.populate(statement.executeQuery("SELECT * FROM " + tableName + " WHERE " + nameColumnId + "=" + id));
            return cachedRowSet;
        }


    public void insert(Statement statement, String tableName, List<String> columns, List<String> values) throws SQLException {
            statement.execute("INSERT INTO " + tableName + "(" + String.join(",", columns) + ")" +
                    " values (" + "'" + StringUtils.join(values, "','") + "'" + ")");

    }

    public CachedRowSet executeQuery(Statement statement, String query) throws SQLException {
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet cachedRowSet = factory.createCachedRowSet();
            cachedRowSet.populate(statement.executeQuery(query));
            return cachedRowSet;
    }


    public void executeUpdate(Statement statement, String query) throws SQLException {
            statement.executeUpdate(query);
    }

    public Long findMaxId(Statement statement, String table, String column) throws SQLException {
        return UtilsParse.idParser(executeQuery(statement, "SELECT max(" + column + ") as id  from " + table));
    }

    public void execute(Statement statement, String query) throws SQLException {
        statement.execute(query);
    }
}
