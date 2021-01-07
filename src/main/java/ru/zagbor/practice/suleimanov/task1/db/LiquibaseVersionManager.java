package ru.zagbor.practice.suleimanov.task1.db;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.SQLException;

public class LiquibaseVersionManager {

    private final static String DATABASE_SCHEMA_CHANGELOG = "WEB-INF\\classes\\db.changelog\\db.data-v.1.0.xml";
    private final static String DATABASE_TEST_DATA_CHANGELOG = "WEB-INF\\classes\\db.changelog\\insert-data.xml";

    public void initDatabaseSchema() throws DatabaseException {
        init(DATABASE_SCHEMA_CHANGELOG);
    }

    public void initTestData() throws DatabaseException {
        init(DATABASE_TEST_DATA_CHANGELOG);
    }

    private void init(String changelog) throws DatabaseException {
        java.sql.Connection c = null;
        try {
            c = ConnectionFactory.getConnection();
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
            new Liquibase(changelog, new ClassLoaderResourceAccessor(), database)
                    .update(new Contexts());
        } catch (DatabaseException e) {
            throw e;
        } catch (LiquibaseException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.rollback();
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
