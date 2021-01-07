package ru.zagbor.practice.suleimanov.task1.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Optional;

public interface GenericRepository<T, ID> {
    Optional<T> getById(ID id);

    Collection<T> getAll() ;

    T update(T t);

    T create(T t);

    void deleteById(ID id);


}
