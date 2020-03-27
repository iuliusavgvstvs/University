package service;

import domain.Copil;
import domain.exceptions.ValidationException;
import domain.validator.CopilValidator;
import repository.CopilDbRepository;

import java.util.ArrayList;

public class CopilService implements IService<Copil>{
    private CopilValidator validator;
    private CopilDbRepository repository;

    public CopilService(CopilValidator val, CopilDbRepository repo){
        this.validator=val;
        this.repository= repo;
    }
    @Override
    public Copil findOne(int id) {
        return repository.findOne(id);
    }

    @Override
    public Copil save(Copil entity) throws ValidationException {
        validator.validate(entity);
        return repository.save(entity);
    }

    public ArrayList<Copil> findByAge(int minAge, int maxAge){
        return repository.findByAge(minAge, maxAge);
    }
}
