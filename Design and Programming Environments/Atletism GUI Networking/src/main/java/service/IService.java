package service;

import domain.Entity;
import domain.exceptions.ValidationException;

public interface IService<E extends Entity> {
    E findOne(int id);

    public E save(E entity) throws ValidationException;
}
