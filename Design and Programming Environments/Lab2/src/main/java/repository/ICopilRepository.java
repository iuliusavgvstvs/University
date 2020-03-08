package repository;

import domain.Entity;


public interface ICopilRepository<ID, E extends Entity<ID>> extends ICrudRepository<ID, E > {
    public Iterable<E> findByProba();

}
