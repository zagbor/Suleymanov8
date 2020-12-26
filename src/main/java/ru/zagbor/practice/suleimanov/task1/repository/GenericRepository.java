package ru.zagbor.practice.suleimanov.task1.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Optional;

public interface GenericRepository<T, ID> {
    Optional<T> getById(ID id) throws IOException, SQLException;

    Collection<T> getAll() throws IOException, SQLException;

    T update(T t) throws IOException, SQLException;

    T create(T t) throws IOException, SQLException;

    void deleteById(ID id) throws IOException, SQLException;


}
