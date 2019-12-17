package service;

import domain.Entity;
import domain.validators.ValidationException;

public interface Service<ID, E extends Entity<ID>> {
    E find(ID id) throws ValidationException;

    Iterable<E> getAll();

    E delete(ID id) throws ValidationException;

    E update(E entity) throws ValidationException;

    public E add(E entity) throws ValidationException, ValidationException;

}
