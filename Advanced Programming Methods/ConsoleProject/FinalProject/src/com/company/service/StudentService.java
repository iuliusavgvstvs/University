package com.company.service;

import com.company.entity.Entity;
import com.company.entity.Student;
import com.company.entity.StudyYear;
import com.company.exceptions.ValidationException;
import com.company.repository.StudentXMLRepo;
import com.company.validator.StudentValidator;

import java.util.HashSet;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StudentService<ID, E extends Entity<ID>> implements Service<ID, Student<ID>> {

    //private GeneralRepository<ID, E> repo;
    private StudentXMLRepo<ID, Student<ID>> repo;
    StudentValidator val;

    public StudentService(String file_name){
       // repo = new GeneralRepository<>();
        //File starting = new File(System.getProperty("C:\\Users\\Iuliu\\OneDrive\\Desktop\\Facultate\\Sem III\\Metode avansate de programare\\Laboratoare\\FinalProject\\src\\com\\company\\files"));
        //File fileToBeRead = new File(starting,"./Students.xml");
        repo = new StudentXMLRepo<>(file_name);
        val = new StudentValidator();
    }

    @Override
    public Student<ID> find(ID id) {
        if(repo.findOne(id) !=null){
            return repo.findOne(id);
        }
        else
            throw new IllegalArgumentException("The student with the id " + id + " doesn't exist.");
    }

    @Override
    public Iterable<Student<ID>> getAll() {
        return repo.findAll();
    }

    @Override
    public Student<ID> delete(ID id) {
        if (repo.findOne(id) != null) {
            return repo.delete(id);
        }
        else
            throw  new IllegalArgumentException("The student doesn't exist.");
    }

    @Override
    public Student<ID> update(Student<ID> entity) throws ValidationException {
        val.validate(entity);
        if (repo.findOne(entity.getId()) != null) {

            return repo.update(entity);
        }
        else
        throw new IllegalArgumentException("Student doesn't exist.");
    }

    @Override
    public Student<ID> add(Student<ID> entity) throws ValidationException {
        val.validate(entity);
        if(repo.findOne(entity.getId())==null) {
            return repo.save( entity);
        }
        else
            throw new IllegalArgumentException("Student already exists.");

    }

    public void motivateWeek(int week, ID id) throws ValidationException {
        Student<ID> student = repo.findOne(id);
        if (student == null)
            throw new IllegalArgumentException("Student doesn't exist.");
        if (week > StudyYear.getSemesterWeek())
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
}
