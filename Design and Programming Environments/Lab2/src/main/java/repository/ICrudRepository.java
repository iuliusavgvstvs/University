package repository;

import domain.Entity;

import java.sql.SQLException;

public interface ICrudRepository<ID, E extends Entity<ID>> {
    E findOne(ID id) throws SQLException;

    Iterable<E> findAll();

    E save(E entity);

    E delete(ID id);

    E update(E entity);

}
