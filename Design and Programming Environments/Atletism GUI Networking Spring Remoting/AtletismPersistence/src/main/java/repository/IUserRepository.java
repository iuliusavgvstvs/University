package repository;

import model.Entity;
import model.User;

public interface IUserRepository<E extends Entity> extends ICrudRepository<E> {
    User getLogin(User entity);

    E findOnebyName(String name);
}
