package repository;

import domain.Entity;

import java.util.ArrayList;

public interface ICopilRepository<E extends Entity> extends ICrudRepository< E > {
    E findOnebyName(String fname, String lname, int age);
    ArrayList<E> findByAge(int minAge, int maxAge);
}
