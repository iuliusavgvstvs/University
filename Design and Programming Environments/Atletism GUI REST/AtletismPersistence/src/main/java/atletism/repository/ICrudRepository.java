package atletism.repository;

import model.Entity;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface ICrudRepository<E extends Entity> {
    E findOne(int id) throws SQLException;

    Iterable<E> findAll();

    E save(E entity);

    void clear();

}
