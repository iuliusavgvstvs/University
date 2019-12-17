package com.company.repository;

import com.company.entity.Entity;

public interface CrudRepository<ID, E extends Entity<ID>> {

    E findOne(ID id);

    Iterable<E> findAll();

    E save(E entity);

    E delete(ID id);

    E update(E entity);
}
