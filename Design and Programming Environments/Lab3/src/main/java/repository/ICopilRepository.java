package repository;


import domain.Entity;

public interface ICopilRepository<E extends Entity> extends ICrudRepository< E > {
    public Iterable<E> findByVarsta();

}
