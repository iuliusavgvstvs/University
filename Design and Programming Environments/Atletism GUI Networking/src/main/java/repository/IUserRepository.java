package repository;

import domain.Entity;
import domain.User;

public interface IUserRepository<E extends Entity> extends ICrudRepository<E> {
    public boolean getLogin(User entity);

    public E findOnebyName(String name);
}
