package repository;

import domain.Entity;

import java.sql.SQLException;

public interface ICrudRepository< E extends Entity> {
    E findOne(int id) throws SQLException;

    Iterable<E> findAll();

    E save(E entity);

}
