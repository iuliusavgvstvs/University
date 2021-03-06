package service;

import domain.Proba;
import domain.exceptions.ValidationException;
import domain.validator.ProbaValidator;
import repository.ProbaDbRepository;

import java.util.ArrayList;

public class ProbaService implements IService<Proba> {
    ProbaValidator valdiator;
    ProbaDbRepository repository;

    public ProbaService(ProbaValidator val, ProbaDbRepository repo) {
        this.valdiator = val;
        this.repository = repo;
    }

    @Override
    public Proba findOne(int id) {
        return repository.findOne(id);
    }

    @Override
    public Proba save(Proba entity) throws ValidationException {
        valdiator.validate(entity);
        return repository.save(entity);
    }

    public ArrayList<Proba> getByCopilID(int id){
        return repository.getByCopilID(id);
    }

}
