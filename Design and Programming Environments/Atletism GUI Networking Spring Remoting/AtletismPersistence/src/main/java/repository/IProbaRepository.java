package repository;

import model.Entity;

import java.util.ArrayList;

public interface IProbaRepository<E extends Entity> extends ICrudRepository<E> {
    ArrayList<E> getByCopilID(int id);

    ArrayList<E> getByDistanta(int distanta);

    E findOneByProba(int idCopil, int distanta);
}
