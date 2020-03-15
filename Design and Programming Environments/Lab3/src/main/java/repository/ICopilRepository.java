package repository;

import domain.Entity;

public interface ICopilRepository<E extends Entity> extends ICrudRepository< E > {
    E findOnebyName(String fname, String lname, int age);

}
