package service;

import domain.Entity;
import domain.Student;
import domain.validators.ValidationException;
import javafx.beans.InvalidationListener;

import repository.StudentXMLRepo;
import domain.validators.StudentValidator;
import utils.events.ChangeEventType;
import utils.events.StudentChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StudentService<ID, E extends Entity<ID>> implements Observable<StudentChangeEvent> {

    private StudentXMLRepo<ID, Student> repo;
    StudentValidator val;
    private List<Observer<StudentChangeEvent>> observers = new ArrayList<>();

    public StudentService(StudentXMLRepo<ID, Student> rep){
        // repo = new GeneralRepository<>();
        //File starting = new File(System.getProperty("C:\\Users\\Iuliu\\OneDrive\\Desktop\\Facultate\\Sem III\\Metode avansate de programare\\Laboratoare\\FinalProject\\src\\com\\company\\files"));
        //File fileToBeRead = new File(starting,"./Students.xml");
        repo = rep;
        val = new StudentValidator();
    }

    public Student<ID> find(ID id) {
        if(repo.findOne(id) !=null){
            return repo.findOne(id);
        }
        else
            throw new IllegalArgumentException("The student with the id " + id + " doesn't exist.");
    }

    public Iterable<Student<ID>> getAll() {
        return repo.findAll();
    }

    public ID getlastID(){
        return repo.getlastID();
    }

    public void setlastID(ID id){
        repo.setLastId(id);
    }

    public Student<ID> delete(ID id) {
        Student st=repo.delete(id);
        if (st != null) {
            notifyObservers(new StudentChangeEvent(ChangeEventType.ADD,st));
        }

        //throw  new IllegalArgumentException("The student doesn't exist.");
        return st;
    }

    public Student<ID> update(Student<ID> entity) throws ValidationException {
        val.validate(entity);
        Student oldst = repo.findOne(entity.getId());
        if (oldst != null) {

            Student newst =repo.update(entity);
            notifyObservers(new StudentChangeEvent(ChangeEventType.ADD, newst));
            return newst;
        }
        else
            return oldst;
        //throw new IllegalArgumentException("Student doesn't exist.");
    }

    public Student<ID> add(Student<ID> entity) throws ValidationException {
        val.validate(entity);
        Student st = repo.save(entity);
        if(st==null){
            notifyObservers(new StudentChangeEvent(ChangeEventType.ADD,st));
        }
        return st;
    }

    public void motivateWeek(int week, ID id) throws ValidationException {
        Student<ID> student = repo.findOne(id);
        if (student == null)
            throw new IllegalArgumentException("Student doesn't exist.");
        if (week > 14)
            throw new IllegalArgumentException("Invalid week.");
        HashSet<Integer> motivations = student.getMotivations();
        if(motivations.contains(week)){
            throw new IllegalArgumentException("Student is already motivated on the week "+week);
        }
        student.motivateWeek(week);
        update(student);
    }
    private static <T> Stream<T> getStreamFromIterable(Iterable<T> iterable)
    {
        Spliterator<T>
                spliterator = iterable.spliterator();
        return StreamSupport.stream(spliterator, false);
    }

    public Stream<Student<ID>> filterGroup(String group) {

        Stream<Student<ID>> result = getStreamFromIterable(this.getAll());
        return result.filter(x->x.getGroup().equals(group));
    }



    public void addListener(InvalidationListener invalidationListener) {

    }

    public void removeListener(InvalidationListener invalidationListener) {

    }


    public void addObserver(Observer<StudentChangeEvent> e) {
        observers.add(e);
    }


    public void removeObserver(Observer<StudentChangeEvent> e) {
        //observers.remove(e);
    }


    public void notifyObservers(StudentChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public Iterable<Student> searchById(Iterable<Student> students, ID id) {
        HashMap<Integer, Student> studs = new HashMap<>();
        for (Student st: students){
            String studentid = (String) st.getId();
            if(studentid.contains((String)id))
                studs.put(Integer.parseInt((String) st.getId()),st);
        }
        return studs.values();
    }

    public Iterable<Student> searchByStringMethod(Iterable<Student> students,String s, Method f) throws InvocationTargetException, IllegalAccessException {
        HashMap<Integer, Student> studs = new HashMap<>();
        for (Student st: students){
            String field = (String)f.invoke(st);
            if(field.contains(s))
                studs.put(Integer.parseInt((String) st.getId()),st);
        }
        return studs.values();
    }

}
