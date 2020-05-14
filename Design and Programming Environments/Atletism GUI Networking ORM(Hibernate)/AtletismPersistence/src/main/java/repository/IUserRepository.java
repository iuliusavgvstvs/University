package repository;

import model.Entity;
import model.User;

public interface IUserRepository<E extends Entity> extends ICrudRepository<E> {
    public User getLogin(User entity);

    public E findOnebyName(String name);
}
