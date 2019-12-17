package repository;

import domain.Entity;

import java.util.HashMap;

public class GeneralRepository<ID, E extends Entity<ID>> implements CrudRepository<ID, E>  {

    private HashMap<Integer, E> repo;

    public HashMap<Integer,E> getRepo(){
        return this.repo;
    }

    public GeneralRepository(){
        repo = new HashMap<>();
    }



    @Override
    public E findOne(ID id) {
        if(id == null){
            throw new IllegalArgumentException("Invalid id!");
        }
        return repo.get(Integer.parseInt((String)id));
    }

    @Override
    public Iterable<E> findAll() {
        return repo.values();
    }

    @Override
    public E save(E entity) {
        return repo.put(Integer.parseInt((String)entity.getId()),entity);
    }

    @Override
    public E delete(ID id) {
        if(id == null){
            throw new IllegalArgumentException("Invalid id!");
        }
        return repo.remove(Integer.parseInt((String)id));
    }

    @Override
    public E update(E entity) {
        return repo.put(Integer.parseInt((String)entity.getId()),entity);
    }
}
