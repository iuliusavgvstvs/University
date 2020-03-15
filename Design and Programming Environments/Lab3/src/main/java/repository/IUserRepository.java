package repository;

import domain.Entity;

public interface IUserRepository<E extends Entity> extends ICrudRepository<E> {
    public boolean getLogin(String username, String password);
    public E findOnebyName(String name);
}
