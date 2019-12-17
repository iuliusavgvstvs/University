package com.company.repository;

import com.company.entity.Entity;

import java.util.HashMap;

public class GeneralRepository<ID, E extends Entity<ID>> implements CrudRepository<ID, E>  {

    private HashMap<ID, E> repo;

    public HashMap<ID,E> getRepo(){
        return this.repo;
    }

    public GeneralRepository(){
        repo = new HashMap<>();
    }

    @Override
    public E findOne(ID id) {
        if(id == null){
            throw new IllegalArgumentException("Invalid id!");
        }
        return repo.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return repo.values();
    }

    @Override
    public E save(E entity) {
        return repo.put(entity.getId(),entity);
    }

    @Override
    public E delete(ID id) {
        if(id == null){
            throw new IllegalArgumentException("Invalid id!");
        }
        return repo.remove(id);
    }

    @Override
    public E update(E entity) {
        return repo.put(entity.getId(),entity);
    }
}
