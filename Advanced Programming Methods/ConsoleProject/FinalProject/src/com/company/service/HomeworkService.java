package com.company.service;

import com.company.entity.Entity;
import com.company.entity.Homework;
import com.company.exceptions.ValidationException;
import com.company.repository.GeneralRepository;
import com.company.repository.HomeworkXMLRepo;
import com.company.validator.HomeworkValidator;

public class HomeworkService<ID, E extends Entity<ID>> implements Service<ID, Homework<ID>> {

    private HomeworkValidator val;
    //private GeneralRepository<ID, E> repo;
    private HomeworkXMLRepo<ID, E> repo;



    public HomeworkService(String file_name){
       // repo = new GeneralRepository<>();
        repo = new HomeworkXMLRepo<>(file_name);
        val = new HomeworkValidator();
    }
    @Override
    public Homework<ID> find(ID id) {
        return repo.findOne(id);
    }

    @Override
    public Iterable<Homework<ID>> getAll() {
        return repo.findAll();
    }

    @Override
    public Homework<ID> delete(ID id) {
        if (repo.findOne(id) != null) {
            return repo.delete(id);
        }
        else
            throw  new IllegalArgumentException("The homework doesn't exist.");
    }

    @Override
    public Homework<ID> update(Homework<ID> entity) throws ValidationException {
        val.validate(entity);
        if(repo.findOne(entity.getId())!=null) {
            return repo.update(entity);
        }
        else
            throw new IllegalArgumentException("The homework desn't exist.");
    }

    @Override
    public Homework<ID> add(Homework<ID> entity) throws ValidationException {
        val.validate(entity);
        if(repo.findOne(entity.getId())!=null){
            throw new IllegalArgumentException("Homework already exists.");
        }
        else
            return repo.save(entity);
    }


}
