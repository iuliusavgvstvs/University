package service;

import domain.Entity;
import domain.Homework;
import domain.Student;
import domain.validators.HomeworkValidator;
import domain.validators.ValidationException;
import repository.HomeworkXMLRepo;
import utils.events.ChangeEventType;
import utils.events.HomeworkChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeworkService<ID, E extends Entity<ID>> implements Observable<HomeworkChangeEvent> {

    private HomeworkValidator val;
    private HomeworkXMLRepo<ID, Homework> repo;
    private List<Observer<HomeworkChangeEvent>> observers = new ArrayList<>();


    public HomeworkService(HomeworkXMLRepo<ID, Homework> rep){
        // repo = new GeneralRepository<>();
        repo = rep;
        val = new HomeworkValidator();
    }

    public Homework<ID> find(ID id) {
        return repo.findOne(id);
    }


    public Iterable<Homework<ID>> getAll() {
        return repo.findAll();
    }


    public ID getlastID(){
        return repo.getlastID();
    }

    public void setlastID(ID id){
        repo.setLastId(id);
    }


    public Homework<ID> delete(ID id) {
        Homework hm = repo.delete(id);
        if (hm != null) {
           notifyObservers(new HomeworkChangeEvent(ChangeEventType.ADD,hm));
        }
        return hm;
    }


    public Homework<ID> update(Homework<ID> entity) throws ValidationException {
        val.validate(entity);
        Homework hm = repo.findOne(entity.getId());
        if(hm != null){
            Homework nhm = repo.update(entity);
            notifyObservers(new HomeworkChangeEvent(ChangeEventType.ADD,nhm));
            return nhm;
        }
        else
            return hm;
    }


    public Homework<ID> add(Homework<ID> entity) throws ValidationException {
        val.validate(entity);
        Homework hm = repo.save(entity);
        if(hm == null){
            notifyObservers(new HomeworkChangeEvent(ChangeEventType.ADD,hm));
        }
        return hm;
    }


    @Override
    public void addObserver(Observer<HomeworkChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<HomeworkChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(HomeworkChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public Iterable<Homework> searchByStringMethod(Iterable<Homework> homeworks, String text, Method f) throws InvocationTargetException, IllegalAccessException {
        HashMap<Integer, Homework> hms = new HashMap<>();
        for (Homework hm: homeworks){
            String field = (String)f.invoke(hm);
            if(field.contains(text))
                hms.put(Integer.parseInt((String) hm.getId()),hm);
        }
        return hms.values();
    }

    public Iterable<Homework> searchByIntegerMethod(Iterable<Homework> homeworks, String integer, Method f) throws InvocationTargetException, IllegalAccessException {
        HashMap<Integer, Homework> hms = new HashMap<>();
        for (Homework hm: homeworks){
            int field = (int) f.invoke(hm);
            String stringfield= Integer.toString(field);
            if(stringfield.contains(integer))
                hms.put(Integer.parseInt((String) hm.getId()),hm);
        }
        return hms.values();
    }
}