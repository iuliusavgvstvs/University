package atletism.repository;

import model.Entity;
import model.Proba;

import java.util.ArrayList;

public interface IProbaRepository<E extends Entity> extends ICrudRepository<E> {
    ArrayList<E> getByCopilID(int id);

    ArrayList<E> getByDistanta(int distanta);

    E findOneByProba(int idCopil, int distanta);

    public void update(int id, Proba proba);

    void delete(String probaname);
}
