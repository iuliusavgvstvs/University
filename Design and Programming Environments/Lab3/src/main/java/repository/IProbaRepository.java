package repository;

import domain.Entity;

import java.util.ArrayList;

public interface IProbaRepository<E extends Entity> extends ICrudRepository<E> {
    public ArrayList<E> getByCopilID(int id);
    public ArrayList<E> getByDistanta(int distanta);
    public E findOneByProba( int idCopil, int distanta);
}
